import React from 'react';
import {DataGrid, GridRowModel, getThemePaletteMode} from '@material-ui/data-grid';
import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import {withStyles, createStyles, createMuiTheme, darken, lighten, Theme} from '@material-ui/core/styles';
import {WithStyles} from '@material-ui/core';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import ListItemText from '@material-ui/core/ListItemText';
import Select from '@material-ui/core/Select';
import Checkbox from '@material-ui/core/Checkbox';
import Chip from '@material-ui/core/Chip';
import Input from '@material-ui/core/Input';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';

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
        formControl: {
            margin: theme.spacing(1),
            minWidth: 150,
            maxWidth: 300,
        },
        chips: {
            display: 'flex',
            flexWrap: 'wrap',
        },
        chip: {
            margin: 2,
        },
        noLabel: {
            marginTop: theme.spacing(3),
        },
    });


const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

class FlightsTable extends React.Component<IProps, IState> {
    private interval: any;
    columnsHeaders = [
        {field: 'scheduledDepartureTime', headerName: 'Scheduled Departure', width: 200},
        {field: 'origin', headerName: "Departure Airport", width: 150},
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
            this.updateFlights();
        }

    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    private updateFlights() {
        let endpoint = this.flightsPortEndPoint(this.props.region, this.props.post, this.props.country, this.props.date, this.props.timezone);
        this.getFlightsData(endpoint, this.updateFlightsData)
    }

    public reqConfig: AxiosRequestConfig = {
        headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'}
    };

    private clearPortFilterFlights() {
        let endpoint = this.flightsEndPoint(this.props.region, this.props.post, this.props.country, this.props.date, this.props.timezone);
        this.getFlightsData(endpoint, this.updateFlightsData)
    }

    public flightsEndPoint(region: string, post: string, country: string, filterDate: string, timezone: string) {
        console.log('......this.state.portName' + this.state.portName)
        return "/flights/" + region + "/" + post + "/" + country + "/" + filterDate + "/" + timezone;
    }

    public flightsPortEndPoint(region: string, post: string, country: string, filterDate: string, timezone: string) {
        console.log('......this.state.portName' + this.state.portName)
        return "/flights/" + region + "/" + post + "/" + country + "/" + filterDate + "/" + timezone + "?portList=" + this.state.portName
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
        this.setState({...this.state, portData: uniquePortData});
        this.setState({...this.state, arrivalRows: arrivalRows});
    }

    handleChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        this.setState({
            portName: event.target.value as string[]
        })
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
                        <Grid container spacing={1}>
                            <Grid item xs={6} sm={2}>
                                <FormControl className={this.props.classes.formControl}>
                                    <InputLabel id="demo-mutiple-checkbox-label">Departure Ports</InputLabel>
                                    <Select
                                        labelId="demo-mutiple-checkbox-label"
                                        id="demo-mutiple-checkbox"
                                        multiple
                                        value={this.state.portName}
                                        onChange={this.handleChange}
                                        input={<Input/>}
                                        renderValue={(selected) => (selected as string[]).join(', ')}
                                        MenuProps={MenuProps}
                                    >
                                        {this.state.portData.map((name) => (
                                            <MenuItem key={name} value={name}>
                                                <Checkbox checked={this.state.portName.indexOf(name) > -1}/>
                                                <ListItemText primary={name}/>
                                            </MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={6} sm={2}>
                                <Button variant="outlined" color="primary" onClick={() => {
                                    this.updateFlights()
                                }}>Ports Filter</Button>
                            </Grid>
                            <Grid item xs={6} sm={2}>
                                <Button variant="outlined" color="secondary" onClick={() => {
                                    this.clearFilter()
                                }}>Clear Ports filter</Button>
                            </Grid>
                            <Grid item xs={6} sm={2}>
                            </Grid>
                        </Grid>
                    </div>
                    <div style={{height: 800, width: '100%'}} className={this.props.classes.root}>
                        <DataGrid disableSelectionOnClick rows={this.state.arrivalRows as GridRowModel[]}
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