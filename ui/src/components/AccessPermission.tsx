import React from 'react';
import Button from '@material-ui/core/Button';
import axios, {AxiosRequestConfig, AxiosResponse} from "axios";


interface Props {
}

interface State {
    checked: boolean;
    hasError: boolean;
    errorMessage: string;
}

class AccessPermission extends React.Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            checked: false,
            hasError: false,
            errorMessage: ''
        };
    }

    handleClick = () => {
        this.setState({checked: true});
        this.requestPermissionByEmail(this.responseData);
    };

    responseData = (response: AxiosResponse) => {
        console.log('response...' + response.data);
    }

    public reqConfig: AxiosRequestConfig = {
        headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'}
    };

    public requestPermissionByEmail(handleResponse: (r: AxiosResponse) => void) {
        axios
            .get("/email/permission", this.reqConfig)
            .then(response => handleResponse(response))
            .catch(t => this.setState(() => ({hasError: true, errorMessage: t})))
    }


    render() {
        if (this.state.checked) {
            if (this.state.hasError) {
                return (
                    <div className="access-permission">
                        <span>Unable to request for access . Please try again later after sometime or contact us at drtpoiseteam@homeoffice.gov.uk</span>
                    </div>
                )
            } else {
                return (
                    <div className="access-permission">
                        <span>You will receive email once you have permission.</span>
                    </div>
                )
            }
        } else {
            return (
                <div className="access-permission">
                    <br/>
                    <br/>
                    <span>You may not have permission to access IEI Dashboard. Please submit request permission by pressing button below.</span>
                    <br/>
                    <br/>
                    <Button variant="contained" onClick={this.handleClick} color="primary" disableElevation>Request
                        Permission</Button>
                </div>
            )
        }

    }
}

export default AccessPermission;