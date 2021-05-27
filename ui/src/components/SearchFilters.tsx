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
      region : 'All',
      country : 'All',
      post : 'All',
      date : props.pickedDate,
      timezone : 'UTC'
    };

  }

  componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
        console.log("this.state.region" + this.state.region);
        if(this.state.region !== prevState.region) {
           this.setState({post : 'All'});
           this.setState({timezone : 'UTC'});
            switch(this.state.region) {
               case 'Euromed North' :
                    regionPosts = euromedNorthPost;
                    console.log("It is a euromedNorthPost.");
                break;
               case 'Euromed South' :
                 regionPosts = euromedSouthPost;
                 console.log("It is a euromedSouthPost.");
                break;
               case 'All' :
                   regionPosts = allPosts;
                   console.log("It is a allPosts.");
                break;
            }
        }

        if (this.state.post !== prevState.post) {
        this.setState({country : 'All'});
        this.setState({timezone : 'UTC'});
            switch (this.state.post) {
                case 'Athens' :
                    postCountries = athensCountries;
                    console.log("It is a athens.");
                    break;
                case 'Benelux':
                    postCountries = beneluxCountries;
                    console.log("It is a beneluxCountries.");
                    break;
                case 'Warsaw':
                    postCountries = warsawCountries;
                    console.log("It is a warsawCountries.");
                    break;
                case 'Berlin':
                    postCountries = berlinCountries;
                    console.log("It is a berlinCountries.");
                    break;
                case 'Paris':
                    postCountries = parisCountries;
                    console.log("It is a parisCountries.");
                    break;
                case 'Rome':
                    postCountries = romeCountries;
                    console.log("It is a romeCountries.");
                    break;
                case 'Madrid':
                     postCountries = madridCountries;
                    console.log("It is a madridCountries.");
                    break;
                case 'Albania':
                    postCountries = albaniaCountries;
                    console.log("It is a albaniaCountries.");
                    break;
                case 'All':
                    postCountries = allCountries;
                    console.log("It is a all countries.");
                    break;
                default:
                    postCountries = allCountries;
                    console.log("No such day exists!");
                    break;
          }
      }
   }

  render()  {
      console.log("SearchFilter...."+ this.state.region + "...." + this.state.post  + "....." +this.state.country + "....."+ this.state.date + "....." + this.state.timezone)
       switch(this.state.country) {
            case 'All' :
               timezones = nonLocalTimezones;
               console.log("this is All country");
               break;
            default :
               timezones = allTimezones;
               console.log("this is local country " + this.state.country);
               break;

       };
      return (
          <div>
              <Grid container spacing={3}>
               <Grid item xs={12} sm={3}>
                 <Autocomplete
                       value = {this.state.region !== null ? {region:this.state.region} : {region:'All'}}
                       onChange= {(event, newValue) => {
                           let {region : newRegionZone} = newValue !== null ? newValue : {region: 'All'}
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
                        value = {this.state.post !== null ? {post:this.state.post} : {post:'All'}}
                        onChange= {(event, newValue) => {
                            let {post : newRegion} = newValue !== null ? newValue : {post: 'All'}
                             this.setState({...this.state, post : newRegion })
                         }}
                        id="post-combo-box"
                        options={regionPosts}
                        getOptionLabel={(option) => option.post}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Post" variant="outlined" />}
                      />
               </Grid>
               <Grid item xs={12} sm={3}>
                    <Autocomplete
                        value = {this.state.country !== null ? {country:this.state.country} : {country:'All'}}
                        onChange= {(event, newValue) => {
                            let {country : newCountry} = newValue !== null ? newValue : {country: 'All'}
                             this.setState({...this.state, country : newCountry })
                         }}
                        id="country-combo-box"
                        options={postCountries}
                        getOptionLabel={(option) => option.country}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Country" variant="outlined" />}
                      />
               </Grid>
               <Grid item xs={12} sm={3}>
                     <Autocomplete
                        value = {this.state.timezone !== null ? {timezone:this.state.timezone} : {timezone:'UTC'}}
                        onChange= {(event, newValue) => {
                            let {timezone : newTimezone} = newValue !== null ? newValue : {timezone: 'UTC'}
                             this.setState({...this.state, timezone : newTimezone })
                         }}
                        id="timezone-combo-box"
                        options={timezones}
                        getOptionLabel={(option) => option.timezone}
                        style={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Departure Timezone" variant="outlined" />}
                      />
               </Grid>
              </Grid>
             <FlightsTable region={this.state.region} post={this.state.post} country={this.state.country} date={this.props.pickedDate} timezone={this.state.timezone} />
           </div>
      );
  }

}

const regions = [
    { region : 'Euromed North'},
    { region : 'Euromed South'},
    { region : 'All'}

]

const allTimezones = [
    { timezone : 'UK'},
    { timezone : 'UTC'},
    { timezone : 'Local'}
]

const nonLocalTimezones = [
    { timezone : 'UK'},
    { timezone : 'UTC'}
]

const euromedNorthPost = [
    { post : 'Benelux'},
    { post : 'Warsaw'},
    { post : 'Berlin'},
    { post : 'Paris'},
    { post : 'All' }
]

const euromedSouthPost = [
     { post : 'Athens'},
     { post : 'Rome'},
     { post : 'Madrid'},
     { post : 'Albania'},
     { post : 'All' }
]

const allPosts = [
    { post : 'Athens'},
    { post : 'Benelux'},
    { post : 'Warsaw'},
    { post : 'Berlin'},
    { post : 'Paris'},
    { post : 'Rome'},
    { post : 'Madrid'},
    { post : 'Albania'},
    { post : 'All'}
]

const athensCountries = [
  { country: 'Greece'},
  { country: 'Cyprus'},
  { country: 'Croatia'},
  { country: 'Slovenia'},
  { country: 'Bulgaria'},
  { country: 'Romania' },
  { country: 'Moldova' },
  { country: 'All'}
]

const beneluxCountries = [
  { country: 'Netherlands'},
  { country: 'Belgium'},
  { country: 'Luxembourg'},
  { country: 'All'}
]

const warsawCountries = [
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
  { country: 'All'}
]

const berlinCountries = [
  { country: 'Germany'},
  { country: 'Austria'},
  { country: 'Switzerland'},
  { country: 'Liechenstein'},
  { country: 'Finland'},
  { country: 'Denmark'},
  { country: 'Norway'},
  { country: 'Sweden'},
  { country: 'All'}
]

const parisCountries = [
  { country: 'France'},
  { country: 'Tunisia'},
  { country: 'Morocco'},
  { country: 'Algeria'},
  { country: 'Basel Mulhouse'},
  { country: 'All'}
]

const romeCountries = [
  { country: 'Italy'},
  { country: 'Malta'},
  { country: 'All'}
]

const madridCountries = [
   { country: 'Spain'},
   { country: 'Portugual'},
   { country: 'All'}
]

const albaniaCountries = [
  { country: 'Albania'},
  { country: 'Serbia'},
  { country: 'North Macedonia'},
  { country: 'Bosnia'},
  { country: 'Kosovo'},
  { country: 'Montenegro'},
  { country: 'All'}
]

const allCountries = [
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

let regionPosts = allPosts
let postCountries = allCountries
let timezones = nonLocalTimezones
