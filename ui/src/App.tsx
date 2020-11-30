import React from 'react';
import './App.css';
import DatePicker from './components/DatePicker'

function App() {
  return (
    <div className="App">
      <header className="App-header">
         <h2>Welcome to DRT IEI Dashboard</h2>
           <DatePicker/>
      </header>
    </div>
  );
}

export default App;
