import React from 'react';
import {DataGrid, GridRowModel ,getThemePaletteMode } from '@material-ui/data-grid';
import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import { withStyles, createStyles, createMuiTheme, darken, lighten, Theme } from '@material-ui/core/styles';
import { WithStyles } from '@material-ui/core';


interface ArrivalsData {
  data: GridRowModel[];
}

interface IProps extends WithStyles<typeof useStyles>  {
  region : string;
  post: string;
  country: string;
  date: string;
  timezone: string;
}

interface IState {
  arrivalRows?: GridRowModel[]
  hasError: boolean;
  errorMessage: string;
}

const getBackgroundColor = (color:string,palette:any) => getThemePaletteMode(palette) === 'dark' ? darken(color, 0.6): lighten(color, 0.6);

const getHoverBackgroundColor = (color:string,palette:any) => getThemePaletteMode(palette) === 'dark'? darken(color, 0.5) : lighten(color, 0.5);

const defaultTheme = createMuiTheme();
const useStyles = (theme: Theme) => createStyles(
{
      root: {
        '& .super-app-theme--Deleted': {
          backgroundColor: getBackgroundColor(theme.palette.warning.main,theme.palette),
          '&:hover': {
            backgroundColor: getHoverBackgroundColor(theme.palette.warning.main,theme.palette),
          },
        },
        '& .super-app-theme--Cancelled': {
          textDecoration : 'line-through',
          backgroundColor: getBackgroundColor(theme.palette.error.main,theme.palette),
          '&:hover': {
            backgroundColor: getHoverBackgroundColor(theme.palette.error.main,theme.palette),
          },
        },
        '& .super-app-theme--Forecast': {
          backgroundColor: getBackgroundColor(theme.palette.info.main,theme.palette),
          '&:hover': {
            backgroundColor: getHoverBackgroundColor(theme.palette.info.main,theme.palette),
          },
        },
        '& .super-app-theme--Active': {
          backgroundColor: getBackgroundColor(theme.palette.success.main,theme.palette),
          '&:hover': {
            backgroundColor: getHoverBackgroundColor(theme.palette.success.main,theme.palette),
             }
         },
        '& .super-app-theme--Others': {
          backgroundColor: getBackgroundColor(theme.palette.primary.main,theme.palette),
          '&:hover': {
            backgroundColor: getHoverBackgroundColor(theme.palette.primary.main,theme.palette),
          },
        },
        defaultTheme
      }
});

class FlightsTable extends React.Component<IProps, IState> {

  columnsHeaders = [
    {field: 'scheduledDepartureTime', headerName: 'Scheduled Departure', width: 200},
    {field: 'origin', headerName: "Departure Airport", width: 150},
    {field: 'flightNumber', headerName: 'Carrier Code', width: 150},
    {field: 'carrierName', headerName: 'Carrier Name', width: 150},
    {field: 'arrivalAirport', headerName: 'Arrival Airport', width: 150},
    {field: 'scheduledArrivalDate', headerName: 'Scheduled Arrival', width: 200},
    {field: 'status', headerName: 'Status', width: 200}

  ]

  constructor(props: IProps) {
    super(props);

    this.state = {
      arrivalRows: [],
      hasError: false,
      errorMessage: ''
    }
  }

  componentDidMount() {
    this.updateFLights();
  }

  componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
    console.log('FlightsTable...componentDidUpdate...' + this.props.country + ' ' +this.props.post + ' ' +this.props.timezone)
    if (this.props.date !== prevProps.date || this.props.country !== prevProps.country || this.props.post !== prevProps.post || this.props.region !== prevProps.region || this.props.timezone !== prevProps.timezone ) {
      this.updateFLights();
    }
  }

  private updateFLights() {
    let endpoint = this.flightsEndPoint(this.props.region, this.props.post, this.props.country, this.props.date, this.props.timezone);
    this.getFlightsData(endpoint, this.updateFlightsData)
  }

  public reqConfig: AxiosRequestConfig = {
    headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'}
  };

  public flightsEndPoint(region: string, post: string, country: string, filterDate: string, timezone:string) {
    return "/flights/" + region + "/" + post + "/" + country + "/"+ filterDate + "/" + timezone
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
    this.setState({...this.state, arrivalRows: arrivalRows});
  }

  render() {
    if (this.state.hasError) {
      throw new Error(this.state.errorMessage);
    } else {
      return (
        <div style={{height: 800 ,width: '100%'}} className={this.props.classes.root}>
             <DataGrid rows={this.state.arrivalRows as GridRowModel[]} columns={this.columnsHeaders} getRowClassName={(params) => `super-app-theme--${params.getValue(params.id,'status')}`} pageSize={25}/>
        </div>
      );
    }
  }
}

export default withStyles(useStyles)(FlightsTable)