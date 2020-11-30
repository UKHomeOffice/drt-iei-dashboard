import React from 'react';
import { DataGrid ,ColDef , RowsProp , RowData} from '@material-ui/data-grid';
import ApiClient from "../services/ApiClient";
import {AxiosResponse} from "axios";

interface ArrivalsData {
  data: RowData[];
}

interface UserLike {
  email: string;
  ports: string[];
}

interface Config {
  ports: string[];
  domain: string;
}

interface IProps {
}

interface IState {
  arrivalRows?: RowData[]
  arrivalsData?: ArrivalsData
  user?: UserLike;
  config?: Config;
}

export default class ArrivalFlightDataTable extends React.Component<IProps, IState> {
  apiClient: ApiClient;

  columnsHeaders = [
    { field: 'id',headerName: "Id", width:100},
    { field: 'arrivalAirport', headerName: 'Arrival Airport',width: 150},
    { field: 'carrierName', headerName: 'Carrier Name', width: 150 },
    { field: 'flightNumber', headerName: 'flight Number', width: 150 },
    { field: 'origin', headerName : "Origin Airport" , width : 150},
    { field: 'scheduledArrivalDate', headerName: 'Scheduled Arrival', width: 200 },
    { field: 'scheduledDepartureTime', headerName: 'Scheduled Departure', width: 200 }
  ]  as ColDef[];

  constructor(props: IProps) {
    super(props);

    this.apiClient = new ApiClient();
    this.state = {
        arrivalRows : []
    };
  }

  componentDidMount() {
    let endpoint = this.apiClient.flightsEndPoint("athens","Romania","2018-12-21");
    this.apiClient.getFlightsData(endpoint,this.updateFlightsData)
  }

  updateFlightsData = (response: AxiosResponse) => {
     console.log(response.data)
      let arrivalsData = response.data as ArrivalsData;
      let arrivalRows = arrivalsData.data as RowData[]
      console.log(arrivalsData.data)
      this.setState({...this.state, arrivalsData: arrivalsData});
      this.setState({...this.state, arrivalRows: arrivalRows});
  }

  updateUserState = (response: AxiosResponse) => {
    let user = response.data as UserLike;
    this.setState({...this.state, user: user});
  }

  updateConfigState = (response: AxiosResponse) => {
    let config = response.data as Config;
    this.setState({...this.state, config: config});
  }

  render() {
    return (
      <header className="App-header">
        <h1>Welcome to DRT</h1>
            <div style={{ height: 1000, width: 1100 }}>
                  <DataGrid rows={this.state.arrivalRows as RowData[]} columns={this.columnsHeaders} pageSize={5} />
             </div>
      </header>
    );
  }
}