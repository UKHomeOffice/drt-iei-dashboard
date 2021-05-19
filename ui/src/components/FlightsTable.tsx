import React from 'react';
import {DataGrid, ColDef, RowData} from '@material-ui/data-grid';
import axios, {AxiosRequestConfig, AxiosResponse} from "axios";

interface ArrivalsData {
  data: RowData[];
}

interface IProps {
  regionZone : string;
  region: string;
  country: string;
  date: string;
  timezone:string;
}

interface IState {
  arrivalRows?: RowData[]
  hasError: boolean;
  errorMessage: string;
}

export default class FlightsTable extends React.Component<IProps, IState> {

  columnsHeaders = [
    {field: 'id', headerName: "Id", width: 50},
    {field: 'arrivalAirport', headerName: 'Arrival Airport', width: 150},
    {field: 'carrierName', headerName: 'Carrier Name', width: 150},
    {field: 'flightNumber', headerName: 'Carrier Code', width: 150},
    {field: 'origin', headerName: "Departure Airport", width: 150},
    {field: 'scheduledArrivalDate', headerName: 'Scheduled Arrival (UTC)', width: 200},
    {field: 'scheduledDepartureTime', headerName: 'Scheduled Departure (UTC+2)', width: 250}
  ] as ColDef[];

  constructor(props: IProps) {
    super(props);

    this.state = {
      arrivalRows: [],
      hasError: false,
      errorMessage: ''
    };
  }

  componentDidMount() {
    this.updateFLights();
  }

  componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
    console.log('FlightsTable...componentDidUpdate...' + this.props.country + ' ' +this.props.region + '' +this.props.timezone)
    if (this.props.date !== prevProps.date || this.props.country !== prevProps.country || this.props.region !== prevProps.region || this.props.regionZone !== prevProps.regionZone ) {
      this.updateFLights();
    }
  }

  private updateFLights() {
    let endpoint = this.flightsEndPoint(this.props.region, this.props.country, this.props.date);
    this.getFlightsData(endpoint, this.updateFlightsData)
  }

  public reqConfig: AxiosRequestConfig = {
    headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'}
  };

  public flightsEndPoint(region: string, country: string, filterDate: string) {
    return "/flights/" + region + "?country=" + country + "&date=" + filterDate
  }

  public getFlightsData(endPoint: string, handleResponse: (r: AxiosResponse) => void) {
    axios
      .get(endPoint, this.reqConfig)
      .then(response => handleResponse(response))
      .catch(t => this.setState(() => ({hasError: true, errorMessage: t})))
  }

  updateFlightsData = (response: AxiosResponse) => {
    let arrivalsData = response.data as ArrivalsData;
    let arrivalRows = arrivalsData.data as RowData[]

    this.setState({...this.state, arrivalRows: arrivalRows});
  }

  render() {
  console.log('FlightsTable...' + this.props.country)
    if (this.state.hasError) {
      throw new Error(this.state.errorMessage);
    } else {
      return (
        <div style={{height: 800 ,width: '100%'}}>
             <DataGrid rows={this.state.arrivalRows as RowData[]} columns={this.columnsHeaders} pageSize={25}/>
        </div>
      );
    }

  }
}
