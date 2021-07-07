import React, {Component, ErrorInfo, ReactNode} from "react";
import AccessPermission from './AccessPermission';

interface Props {
    children: ReactNode;
}

interface State {
    hasError: boolean;
    errorMessage: string;
}

class ErrorBoundary extends Component<Props, State> {

    constructor(props: Props) {
        super(props);
        this.state = {
            hasError: false,
            errorMessage: ''
        };
    }


    public static getDerivedStateFromError(error: Error): State {
        // Update state so the next render will show the fallback UI.
        return {hasError: true, errorMessage: error.message};
    }

    public componentDidCatch(error: Error, errorInfo: ErrorInfo) {
        this.setState({hasError: true});
        this.setState({errorMessage: error.message});
        console.error("error : ", error, errorInfo);
    }

    public render() {
        if (this.state.hasError) {
            if (this.state.errorMessage.includes('status code 403') || this.state.errorMessage.includes('status code 404'))
                return <AccessPermission/>
            else
                return <p>Sorry, DRT IEI is having some issues right now. Please try again later or contact support at
                    drtpoiseteam@homeoffice.gov.uk</p>
        }
        return this.props.children;
    }
}

export default ErrorBoundary;
