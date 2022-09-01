import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import ReactGA from 'react-ga4';

console.log('process.env.NODE_ENV ' + process.env.NODE_ENV)

if (process.env.REACT_APP_GA_MEASURE_ID) {
    ReactGA.initialize(process.env.REACT_APP_GA_MEASURE_ID || '');
    ReactGA.send("pageview");
}
ReactDOM.render(
    <React.StrictMode>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
        <App/>
    </React.StrictMode>,
    document.getElementById('root')
);

