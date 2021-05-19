import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import FlightsTable from './FlightsTable'
import Grid from '@material-ui/core/Grid';

interface IState {
  date : string;
  regionZone : string;
  region : string;
  country : string;
  timezone : string;
}

interface IProps{
    pickedDate: string;
}

export default class SearchFilters extends React.Component<IProps, IState> {

  constructor(props: IProps) {
    super(props);

    this.state = {
      regionZone : 'Euromed South',
      country : 'Greece',
      region : 'athens',
      date : props.pickedDate,
      timezone : 'UK'
    };

  }

  render()  {
      console.log("SearchFilter...."+ this.state.region + +"....." +this.state.country + "....."+ this.state.date + "....." + this.state.timezone)
      return (
          <div>
              <Grid container spacing={3}>
               <Grid item xs={12} sm={3}>
                 <Autocomplete
                       value = {this.state.regionZone !== null ? {regionZone:this.state.regionZone} : {regionZone:''}}
                       onChange= {(event, newValue) => {
                           console.log("Region Zone...." +this.state.regionZone + "....."+ newValue)
                           let {regionZone : newRegionZone} = newValue !== null ? newValue : {regionZone: ''}
                            this.setState({...this.state, regionZone : newRegionZone })
                        }}
                       id="regionZone-combo-box"
                       options={regionZones}
                       getOptionLabel={(option) => option.regionZone}
                       style={{ width: 200 }}
                       renderInput={(params) => <TextField {...params} label="RegionZone" variant="outlined" />}
                     />
               </Grid>
               <Grid item xs={12} sm={3}>
                    <Autocomplete
                        value = {this.state.region !== null ? {region:this.state.region} : {region:''}}
                        onChange= {(event, newValue) => {
                            console.log("Region...." +this.state.region + "....."+ newValue)
                            let {region : newRegion} = newValue !== null ? newValue : {region: ''}
                             this.setState({...this.state, region : newRegion })
                         }}
                        id="region-combo-box"
                        options={regions}
                        getOptionLabel={(option) => option.region}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Region" variant="outlined" />}
                      />
               </Grid>
               <Grid item xs={12} sm={3}>
                    <Autocomplete
                        value = {this.state.country !== null ? {country:this.state.country} : {country:''}}
                        onChange= {(event, newValue) => {
                            console.log("Country...." +this.state.country + "....."+ newValue)
                            let {country : newCountry} = newValue !== null ? newValue : {country: ''}
                             this.setState({...this.state, country : newCountry })
                         }}
                        id="country-combo-box"
                        options={countries}
                        getOptionLabel={(option) => option.country}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Country" variant="outlined" />}
                      />
               </Grid>
               <Grid item xs={12} sm={3}>
                     <Autocomplete
                        value = {this.state.timezone !== null ? {timezone:this.state.timezone} : {timezone:''}}
                        onChange= {(event, newValue) => {
                            console.log("timezone...." +this.state.timezone + "....."+ newValue)
                            let {timezone : newTimezone} = newValue !== null ? newValue : {timezone: ''}
                             this.setState({...this.state, timezone : newTimezone })
                         }}
                        id="timezone-combo-box"
                        options={timezones}
                        getOptionLabel={(option) => option.timezone}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Timezone" variant="outlined" />}
                      />
               </Grid>
              </Grid>
             <FlightsTable region={this.state.region} country={this.state.country} date={this.props.pickedDate} timezone={this.state.timezone} regionZone={this.state.regionZone}/>
           </div>
      );
  }

}

const regionZones = [
    { regionZone : 'Euromed North'},
    { regionZone : 'Euromed South'},

]
const timezones = [
    { timezone : 'UK'},
    { timezone : 'UTC'},
    { timezone : 'Local'}
]

const regions = [
    { region : 'athens'},
    { region : 'benelux'},
    { region : 'warsaw'},
    { region : 'berlin'},
    { region : 'paris'},
    { region : 'rome'},
    { region : 'madrid'},
    { region : 'albania'}
]

const countries = [
  { country: 'Greece'},
  { country: 'Cyprus'},
  { country: 'Croatia'},
  { country: 'Slovenia'},
  { country: 'Bulgaria'},
  { country: 'Romania' },
  { country: 'Moldova' },
  { country: 'Netherlands'},
  { country: 'Belgium'},
  { country: 'Luxembourg'},
];
