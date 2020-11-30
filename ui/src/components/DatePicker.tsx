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
 // eslint-disable-next-line
import format from 'date-fns/format';

export default function MaterialUIPickers() {
  // The first commit of Material-UI
  const [selectedDate, setSelectedDate] = React.useState<Date | null>(
    new Date('2018-12-21T01:11:54'),
  );

  const handleDateChange = (date: Date | null) => {
    setSelectedDate(date);
  };


  return (
    <MuiPickersUtilsProvider utils={DateFnsUtils}>
        <KeyboardDatePicker
          margin="normal"
          id="date-picker-dialog"
          label="Flight departure date"
          format="yyyy-MM-dd"
          value={selectedDate}
          onChange={handleDateChange}
          KeyboardButtonProps={{
            'aria-label': 'change date',
          }}
        />
        <CountryTab pickedDate={ format(selectedDate as Date,"yyyy-MM-dd") } />
    </MuiPickersUtilsProvider>
  );
}
