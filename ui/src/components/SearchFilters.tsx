import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';

interface IState {
  country : string;
  date : string;
}

interface IProps{
    pickedDate: string;
}

export default class SearchFilters extends React.Component<IProps, IState> {

  constructor(props: IProps) {
    super(props);

    this.state = {
      country : 'greece',
      date : props.pickedDate
    };

  }

  render()  {
      return (
          <Autocomplete
              value = {this.state.country !== null ? {country:this.state.country} : {country:''}}
              onChange= {(event, newValue) => {
                  console.log("...." + newValue)
                  let {country : newCountry} = newValue !== null ? newValue : {country: ''}
                   this.setState({...this.state, country : newCountry })
               }}
              id="combo-box-demo"
              options={countries}
              getOptionLabel={(option) => option.country}
              style={{ width: 200 }}
              renderInput={(params) => <TextField {...params} label="Country name" variant="outlined" />}
            />
      );
  }

}


const countries = [
  { country: 'greece'},
  { country: 'cyprus'},
  { country: 'croatia'},
  { country: 'slovenia'},
  { country: 'bulgaria'},
  { country: 'romania' },
  { country: 'moldova' },
  { country: 'netherlands'},
  { country: 'belgium'},
  { country: 'luxembourg'},
];
