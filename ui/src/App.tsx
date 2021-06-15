import React from 'react';
import './App.css';
import DatePicker from './components/DatePicker'
import ErrorBoundary from './components/ErrorBoundary'


function App() {
    return (
        <div className="App">
            <div className="image-div">
                <img src="favicon.png" alt="" className="image-homeOffice"/>
                <span className="image-text">Home Office</span>
            </div>
            <header className="App-header">
                <h2>DRT IEI Dashboard</h2>
                <ErrorBoundary>
                    <DatePicker/>
                </ErrorBoundary>
            </header>
        </div>
    );
}

export default App;
