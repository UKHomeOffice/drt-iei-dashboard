import React from 'react';
import './App.css';
import DatePicker from './components/DatePicker'
import ErrorBoundary from './components/ErrorBoundary'
import Grid from '@material-ui/core/Grid';

function App() {
    let logoutUrl = '/oauth/logout?redirect='+window.location.href;
    return (
        <div className="App">
            <header className="App-header">
                <Grid container>
                    <Grid item xs={12} sm={4}>
                        <div className="image-div">
                            <img src="favicon.png" alt="" className="image-homeOffice"/>
                            <span className="image-text">Home Office</span>
                        </div>
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <h3>DRT IEI Dashboard</h3>
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <div className="logout">
                            <a href={logoutUrl}>Log out</a>
                        </div>
                    </Grid>
                </Grid>
                <ErrorBoundary>
                    <DatePicker/>
                </ErrorBoundary>
            </header>
        </div>
    );
}

export default App;
