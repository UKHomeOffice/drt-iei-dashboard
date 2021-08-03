import React from 'react';
import {DataGrid, GridRowModel, getThemePaletteMode, GridToolbar} from '@material-ui/data-grid';
import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import {withStyles, createStyles, createMuiTheme, darken, lighten, Theme} from '@material-ui/core/styles';
import {WithStyles} from '@material-ui/core';
import Checkbox from '@material-ui/core/Checkbox';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import CheckBoxIcon from '@material-ui/icons/CheckBox';


interface FlightData {
    origin: string;
    arrivalAirport: string;
    flightNumber: string;
    carrierName: string;
    status: string;
    scheduledArrivalDate: string;
    scheduledDepartureTime: string;
}

interface ArrivalsData {
    data: GridRowModel[];
}

interface IProps extends WithStyles<typeof useStyles> {
    region: string;
    post: string;
    country: string;
    date: string;
    timezone: string;
}

interface IState {
    arrivalRows?: GridRowModel[]
    hasError: boolean;
    errorMessage: string;
    currentTime: string;
    portData: string[];
    portName: string[];
    flightData: FlightData[];
}

const getBackgroundColor = (color: string, palette: any) => getThemePaletteMode(palette) === 'dark' ? darken(color, 0.6) : lighten(color, 0.6);

const getHoverBackgroundColor = (color: string, palette: any) => getThemePaletteMode(palette) === 'dark' ? darken(color, 0.5) : lighten(color, 0.5);

const defaultTheme = createMuiTheme();
const useStyles = (theme: Theme) => createStyles(
    {
        root: {
            '& .super-app-theme--Deleted': {
                backgroundColor: getBackgroundColor(theme.palette.secondary.main, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.secondary.main, theme.palette),
                },
            },
            '& .super-app-theme--Cancelled': {
                textDecoration: 'line-through',
                backgroundColor: getBackgroundColor(theme.palette.error.main, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.error.main, theme.palette),
                },
            },
            '& .super-app-theme--Forecast': {
                backgroundColor: getBackgroundColor(theme.palette.info.main, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.info.main, theme.palette),
                },
            },
            '& .super-app-theme--Active': {
                backgroundColor: getBackgroundColor(theme.palette.success.main, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.success.main, theme.palette),
                }
            },
            '& .super-app-theme--Scheduled': {
                backgroundColor: getBackgroundColor(theme.palette.primary.main, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.primary.main, theme.palette),
                },
            },
            '& .super-app-theme--Others': {
                backgroundColor: getBackgroundColor(theme.palette.warning.main, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.warning.main, theme.palette),
                },
            },
            '& .super-app-theme--No_Pax_Info': {
                backgroundColor: getBackgroundColor(theme.palette.info.dark, theme.palette),
                '&:hover': {
                    backgroundColor: getHoverBackgroundColor(theme.palette.info.dark, theme.palette),
                },
            },
            defaultTheme
        },
        extendedIcon: {
            marginRight: theme.spacing(1),
        },
    });

const icon = <CheckBoxOutlineBlankIcon fontSize="small"/>;
const checkedIcon = <CheckBoxIcon fontSize="small"/>;

class FlightsTable extends React.Component<IProps, IState> {
    private interval: any;
    columnsHeaders = [
        {field: 'scheduledDepartureTime', headerName: 'Scheduled Departure', width: 190},
        {field: 'origin', headerName: "Departure Airport", width: 160},
        {field: 'flightNumber', headerName: 'Carrier Code', width: 150},
        {field: 'carrierName', headerName: 'Carrier Name', width: 150},
        {field: 'arrivalAirport', headerName: 'Arrival Airport', width: 150},
        {field: 'scheduledArrivalDate', headerName: 'Scheduled Arrival', width: 200},
        {field: 'status', headerName: 'Status', width: 100}

    ]

    constructor(props: IProps) {
        super(props);
        this.state = {
            arrivalRows: [],
            hasError: false,
            errorMessage: '',
            currentTime: new Date().toLocaleString(),
            flightData: [],
            portData: [],
            portName: []
        }
    }

    componentDidMount() {
        this.interval = setInterval(() => {
            this.setState({
                currentTime: new Date().toLocaleString()
            })
        }, 60000);
        this.updateFlights();
    }

    componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
        console.log('FlightsTable componentDidUpdate...' + this.props.country + ' ' + this.props.post + ' ' + this.props.timezone + ' ' + this.state.currentTime)
        if (this.props.date !== prevProps.date || this.props.country !== prevProps.country || this.props.post !== prevProps.post || this.props.region !== prevProps.region) {
            this.clearFilter();
        }

        if (this.props.timezone !== prevProps.timezone) {
            this.updateFlightsWithoutPort();
        }

    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    private updateFlights() {
        const endpoint = this.flightsPortEndPoint(this.props.region, this.props.post, this.props.country, this.props.date, this.props.timezone);
        this.getFlightsData(endpoint, this.updateFlightsData)
    }

    private updateFlightsWithoutPort() {
        const endpoint = this.flightsPortEndPoint(this.props.region, this.props.post, this.props.country, this.props.date, this.props.timezone);
        this.getFlightsData(endpoint, this.updateFlightsDataWithoutPortData)
    }

    private clearPortFilterFlights() {
        const endpoint = this.flightsEndPoint(this.props.region, this.props.post, this.props.country, this.props.date, this.props.timezone);
        this.getFlightsData(endpoint, this.updateFlightsData)
    }

    public reqConfig: AxiosRequestConfig = {
        headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'}
    };

    public flightsEndPoint(region: string, post: string, country: string, filterDate: string, timezone: string) {
        return "/flights/" + region + "/" + post + "/" + country + "/" + filterDate + "/" + timezone;
    }

    public flightsPortEndPoint(region: string, post: string, country: string, filterDate: string, timezone: string) {
        return this.flightsEndPoint(region, post, country, filterDate, timezone) + "?portList=" + this.state.portName
    }

    public getFlightsData(endPoint: string, handleResponse: (r: AxiosResponse) => void) {
        axios
            .get(endPoint, this.reqConfig)
            .then(response => handleResponse(response))
            .catch(t => this.setState(() => ({hasError: true, errorMessage: t})))
    }

    updateFlightsData = (response: AxiosResponse) => {
        let arrivalsData = response.data as ArrivalsData;
        let arrivalRows = arrivalsData.data as GridRowModel[]
        let flightData = arrivalsData.data as FlightData[]
        let uniquePortData = this.uniqueArray(flightData.map(a => a.origin))
        this.setState({...this.state, flightData: flightData});
        this.setState({...this.state, portData: uniquePortData});
        this.setState({...this.state, arrivalRows: arrivalRows});
    }

    updateFlightsDataWithoutPortData = (response: AxiosResponse) => {
        let arrivalsData = response.data as ArrivalsData;
        let arrivalRows = arrivalsData.data as GridRowModel[]
        this.setState({...this.state, arrivalRows: arrivalRows});
    }

    handleChange = (event: React.ChangeEvent<{}>, value: string[]) => {
        this.setState({
            portName: value
        }, () => this.updateFlightsWithoutPort())
    };

    clearFilter() {
        this.setState({portName: []});
        this.clearPortFilterFlights();
    }

    uniqueArray(a: string[]) {
        return Array.from(new Set(a));
    };

    render() {
        if (this.state.hasError) {
            throw new Error(this.state.errorMessage);
        } else {
            return (
                <div>
                    <div>
                        <Grid container>
                            <Grid item xs={12} sm={4}/>
                            <Grid item xs={12} sm={4}>
                                <Autocomplete
                                    multiple
                                    id="checkboxes-tags-port"
                                    options={this.state.portData}
                                    disableCloseOnSelect
                                    getOptionLabel={(option) => option}
                                    renderOption={(option, {selected}) => (
                                        <React.Fragment>
                                            <Checkbox
                                                icon={icon}
                                                checkedIcon={checkedIcon}
                                                style={{marginRight: 5}}
                                                checked={selected}
                                            />
                                            {option}
                                        </React.Fragment>
                                    )}
                                    renderInput={(params) => (
                                        <TextField {...params} variant="outlined" label="Departure Port"/>
                                    )}
                                    onChange={this.handleChange}
                                    value={this.state.portName}
                                    style={{width: "85%", marginLeft: 25}}
                                />
                            </Grid>
                            <Grid item xs={12} sm={4}/>
                        </Grid>
                    </div>
                    <br/>
                    <div style={{height: 800, width: '100%'}} className={this.props.classes.root}>
                        <DataGrid disableColumnMenu
                                  components={{Toolbar: GridToolbar}}
                                  disableSelectionOnClick
                                  rows={this.state.arrivalRows as GridRowModel[]}
                                  columns={this.columnsHeaders}
                                  getRowClassName={(params) => `super-app-theme--${params.getValue(params.id, 'status')}`}
                                  pageSize={25}/>
                    </div>
                </div>
            );
        }
    }
}

export default withStyles(useStyles)(FlightsTable)