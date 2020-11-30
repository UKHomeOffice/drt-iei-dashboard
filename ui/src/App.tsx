import React from 'react';
import './App.css';
import CountryTab from './components/CountryTab'

function App() {
  return (
    <div className="App">
      <header className="App-header">
         <h2>Welcome to DRT IEI Dashboard</h2>
          <CountryTab/>
      </header>
    </div>
  );
}

export default App;
