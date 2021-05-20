import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import FlightsTable from './FlightsTable'
import Grid from '@material-ui/core/Grid';

interface IState {
  date : string;
  region : string;
  post : string;
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
      region : 'Euromed South',
      country : 'Greece',
      post : 'athens',
      date : props.pickedDate,
      timezone : 'UK'
    };

  }

  render()  {
      console.log("SearchFilter...."+ this.state.post + +"....." +this.state.country + "....."+ this.state.date + "....." + this.state.timezone)
      return (
          <div>
              <Grid container spacing={3}>
               <Grid item xs={12} sm={3}>
                 <Autocomplete
                       value = {this.state.region !== null ? {region:this.state.region} : {region:''}}
                       onChange= {(event, newValue) => {
                           console.log("Region ...." +this.state.region + "....."+ newValue)
                           let {region : newRegionZone} = newValue !== null ? newValue : {region: ''}
                            this.setState({...this.state, region : newRegionZone })
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
                        value = {this.state.post !== null ? {post:this.state.post} : {post:''}}
                        onChange= {(event, newValue) => {
                            console.log("Post...." +this.state.post + "....."+ newValue)
                            let {post : newRegion} = newValue !== null ? newValue : {post: ''}
                             this.setState({...this.state, post : newRegion })
                         }}
                        id="post-combo-box"
                        options={posts}
                        getOptionLabel={(option) => option.post}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Post" variant="outlined" />}
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
             <FlightsTable post={this.state.post} country={this.state.country} date={this.props.pickedDate} timezone={this.state.timezone} region={this.state.region}/>
           </div>
      );
  }

}

const regions = [
    { region : 'Euromed North'},
    { region : 'Euromed South'},

]
const timezones = [
    { timezone : 'UK'},
    { timezone : 'UTC'},
    { timezone : 'Local'}
]

const posts = [
    { post : 'athens'},
    { post : 'benelux'},
    { post : 'warsaw'},
    { post : 'berlin'},
    { post : 'paris'},
    { post : 'rome'},
    { post : 'madrid'},
    { post : 'albania'},
    { post : 'all'}
]

const countries = [
  { country: 'Netherlands'},
  { country: 'Belgium'},
  { country: 'Luxembourg'},
  { country: 'Poland'},
  { country: 'Czech Republic'},
  { country: 'Slovakia'},
  { country: 'Ukraine'},
  { country: 'Russia'},
  { country: 'Belarus'},
  { country: 'Latvia'},
  { country: 'Estonia'},
  { country: 'Lithuania'},
  { country: 'Iceland'},
  { country: 'Germany'},
  { country: 'Austria'},
  { country: 'Switzerland'},
  { country: 'Liechenstein'},
  { country: 'Finland'},
  { country: 'Denmark'},
  { country: 'Norway'},
  { country: 'Sweden'},
  { country: 'France'},
  { country: 'Tunisia'},
  { country: 'Morocco'},
  { country: 'Algeria'},
  { country: 'Basel Mulhouse'},
  { country: 'Italy'},
  { country: 'Malta'},
  { country: 'Greece'},
  { country: 'Cyprus'},
  { country: 'Croatia'},
  { country: 'Slovenia'},
  { country: 'Bulgaria'},
  { country: 'Romania' },
  { country: 'Moldova' },
  { country: 'Spain'},
  { country: 'Portugual'},
  { country: 'Albania'},
  { country: 'Serbia'},
  { country: 'North Macedonia'},
  { country: 'Bosnia'},
  { country: 'Kosovo'},
  { country: 'Montenegro'},
  { country: 'All'}
];
