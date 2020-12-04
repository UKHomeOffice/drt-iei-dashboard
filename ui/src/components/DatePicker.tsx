import 'date-fns';
import React from 'react';
 // eslint-disable-next-line
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker,
} from '@material-ui/pickers';
import CountryTab from './CountryTab';
import format from 'date-fns/format';

interface IProps {
}

interface IState {
 selectedDate?: Date
}

export default class MaterialUIPickers extends React.Component<IProps, IState> {

  handleDateChange = (sDate: Date | null) => {
    let selectedDate = sDate as Date
    this.setState({...this.state, selectedDate: selectedDate});
  };

  constructor(props: IProps) {
      super(props);
     this.state = {
      selectedDate :  new Date()
     };
    }

  componentDidMount() {
    console.log("this.state.selectedDate" + this.state.selectedDate)
   }

 render() {
     return (
         <MuiPickersUtilsProvider utils={DateFnsUtils}>
             <KeyboardDatePicker
               margin="normal"
               id="date-picker-dialog"
               label="Flight departure date"
               format="yyyy-MM-dd"
               value={this.state.selectedDate}
               onChange={this.handleDateChange}
               KeyboardButtonProps={{
                 'aria-label': 'change date',
               }}
             />
             <CountryTab pickedDate={ format(this.state.selectedDate as Date,"yyyy-MM-dd") } />
         </MuiPickersUtilsProvider>
       );
 }
}
