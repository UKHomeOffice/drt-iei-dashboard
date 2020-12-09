import axios, {AxiosRequestConfig, AxiosResponse} from "axios";


interface IApiClient {
  getFlightsData: (endPoint: string, handleResponse: (r: AxiosResponse) => void) => void
  flightsEndPoint: (region: string, country: string, filterDate: string) => string
}

export default class ApiClient implements IApiClient {

  public userEndPoint = "/api/user";
  public configEndPoint = "/api/config";
  public requestAccessEndPoint = "/api/request-access";

  public reqConfig : AxiosRequestConfig = {
        headers: {'Access-Control-Allow-Origin': '*','Content-Type': 'application/json'}
    };
  public flightsEndPoint(region: string,country: string,filterDate: string) { return "/flights/" +region+"?country=" + country + "&date=" + filterDate }

  public getFlightsData(endPoint: string, handleResponse: (r: AxiosResponse) => void) {
    axios
      .get(endPoint, this.reqConfig)
      .then(response => handleResponse(response))
      .catch(t => this.handleAjaxException(endPoint, t))
  }

  public sendData(userEndPoint: string, data: any, handleResponse: (r: AxiosResponse) => void) {
    axios
      .post("/api/request-access", data)
      .then(r => handleResponse(r))
      .catch(t => console.log('caught: ' + t))
  }

  handleAjaxException(endPoint: string, throwable: any) {
    console.log('caught: ' + throwable);
    window.document.location.reload();
  }
}