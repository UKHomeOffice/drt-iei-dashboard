import React from 'react';
import './App.css';
import DatePicker from './components/DatePicker'
import ErrorBoundary from './components/ErrorBoundary'
function App() {
  return (
    <div className="App">
      <header className="App-header">
         <h2>Welcome to DRT IEI Dashboard</h2>
            <ErrorBoundary>
               <DatePicker/>
           </ErrorBoundary>
      </header>
    </div>
  );
}

export default App;
