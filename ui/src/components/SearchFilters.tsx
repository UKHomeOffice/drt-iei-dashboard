import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import FlightsTable from './FlightsTable'
import Grid from '@material-ui/core/Grid';

interface IState {
    date: string;
    region: string;
    post: string;
    country: string;
    timezone: string;
}

interface IProps {
    pickedDate: string;
}

export default class SearchFilters extends React.Component<IProps, IState> {

    constructor(props: IProps) {
        super(props);

        this.state = {
            region: 'All',
            country: 'All',
            post: 'All',
            date: props.pickedDate,
            timezone: 'UTC'
        };

    }

    componentDidUpdate(prevProps: Readonly<IProps>, prevState: Readonly<IState>, snapshot?: any) {
        console.log("this.state.region" + this.state.region);
        if (this.state.region !== prevState.region) {
            this.setState({post: 'All'});
            this.setState({timezone: 'UTC'});
            switch (this.state.region) {
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
            this.setState({country: 'All'});
            this.setState({timezone: 'UTC'});
            switch (this.state.post) {
                case 'Athens' :
                    postCountries = athensCountries;
                    break;
                case 'Benelux':
                    postCountries = beneluxCountries;
                    break;
                case 'Warsaw':
                    postCountries = warsawCountries;
                    break;
                case 'Berlin':
                    postCountries = berlinCountries;
                    break;
                case 'Paris':
                    postCountries = parisCountries;
                    break;
                case 'Rome':
                    postCountries = romeCountries;
                    break;
                case 'Madrid':
                    postCountries = madridCountries;
                    break;
                case 'Albania':
                    postCountries = albaniaCountries;
                    break;
                case 'Dublin':
                    postCountries = dublinCountries;
                    break;
                case 'All':
                    postCountries = allCountries;
                    break;
                default:
                    postCountries = allCountries;
                    break;
            }
        }

        if (this.state.country !== prevState.country) {
            if (this.state.country === 'All') {
                this.setState({timezone: 'UTC'});
            }
        }
    }

    render() {
        switch (this.state.country) {
            case 'All' :
                timezones = nonLocalTimezones;
                console.log("this is All country");
                break;
            default :
                timezones = allTimezones;
                console.log("this is local country " + this.state.country);
                break;
        }
        ;
        console.log("SearchFilter...." + this.state.region + "...." + this.state.post + "....." + this.state.country + "....." + this.state.date + "....." + this.state.timezone)
        return (
            <div>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={3}>
                        <Autocomplete
                            value={this.state.region !== null ? {region: this.state.region} : {region: 'All'}}
                            onChange={(event, newValue) => {
                                let {region: newRegionZone} = newValue !== null ? newValue : {region: 'All'}
                                this.setState({...this.state, region: newRegionZone})
                            }}
                            id="region-combo-box"
                            options={regions}
                            getOptionLabel={(option) => option.region}
                            style={{width: 200}}
                            renderInput={(params) => <TextField {...params} label="Region" variant="outlined"/>}
                        />
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <Autocomplete
                            value={this.state.post !== null ? {post: this.state.post} : {post: 'All'}}
                            onChange={(event, newValue) => {
                                let {post: newRegion} = newValue !== null ? newValue : {post: 'All'}
                                this.setState({...this.state, post: newRegion})
                            }}
                            id="post-combo-box"
                            options={regionPosts}
                            getOptionLabel={(option) => option.post}
                            style={{width: 200}}
                            renderInput={(params) => <TextField {...params} label="Post" variant="outlined"/>}
                        />
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <Autocomplete
                            value={this.state.country !== null ? {country: this.state.country} : {country: 'All'}}
                            onChange={(event, newValue) => {
                                let {country: newCountry} = newValue !== null ? newValue : {country: 'All'}
                                this.setState({...this.state, country: newCountry})
                            }}
                            id="country-combo-box"
                            options={postCountries}
                            getOptionLabel={(option) => option.country}
                            style={{width: 200}}
                            renderInput={(params) => <TextField {...params} label="Country" variant="outlined"/>}
                        />
                    </Grid>
                    <Grid item xs={12} sm={3}>
                        <Autocomplete
                            value={this.state.timezone !== null ? {timezone: this.state.timezone} : {timezone: 'UTC'}}
                            onChange={(event, newValue) => {
                                let {timezone: newTimezone} = newValue !== null ? newValue : {timezone: 'UTC'}
                                this.setState({...this.state, timezone: newTimezone})
                            }}
                            id="timezone-combo-box"
                            options={timezones}
                            getOptionLabel={(option) => option.timezone}
                            style={{width: 200}}
                            renderInput={(params) => <TextField {...params} label="Display Timezone"
                                                                variant="outlined"/>}
                        />
                    </Grid>
                    <Grid item xs={12}/>
                </Grid>
                <FlightsTable region={this.state.region} post={this.state.post} country={this.state.country}
                              date={this.props.pickedDate} timezone={this.state.timezone}/>
            </div>
        );
    }

}

const regions = [
    {region: 'Euromed North'},
    {region: 'Euromed South'},
    {region: 'All'}

]

const allTimezones = [
    {timezone: 'UK'},
    {timezone: 'UTC'},
    {timezone: 'Local'}
]

const nonLocalTimezones = [
    {timezone: 'UK'},
    {timezone: 'UTC'}
]

const euromedNorthPost = [
    {post: 'All'}
    {post: 'Benelux'},
    {post: 'Berlin'},
    {post: 'Paris'},
    {post: 'Warsaw'},
]

const euromedSouthPost = [
    {post: 'All'}
    {post: 'Athens'},
    {post: 'Albania'},
    {post: 'Dublin'},
    {post: 'Madrid'},
    {post: 'Rome'},
]

const allPosts = [
    {post: 'All'}
    {post: 'Athens'},
    {post: 'Albania'},
    {post: 'Benelux'},
    {post: 'Berlin'},
    {post: 'Dublin'},
    {post: 'Madrid'},
    {post: 'Paris'},
    {post: 'Rome'},
    {post: 'Warsaw'},
]

const athensCountries = [
    {country: 'All'}
    {country: 'Bulgaria'},
    {country: 'Croatia'},
    {country: 'Cyprus'},
    {country: 'Greece'},
    {country: 'Moldova'},
    {country: 'Romania'},
    {country: 'Slovenia'},
]

const beneluxCountries = [
    {country: 'All'}
    {country: 'Belgium'},
    {country: 'Luxembourg'},
    {country: 'Netherlands'},
]

const warsawCountries = [
    {country: 'All'}
    {country: 'Belarus'},
    {country: 'Czech Republic'},
    {country: 'Estonia'},
    {country: 'Iceland'},
    {country: 'Latvia'},
    {country: 'Lithuania'},
    {country: 'Poland'},
    {country: 'Russia'},
    {country: 'Slovakia'},
    {country: 'Ukraine'},
]

const berlinCountries = [
    {country: 'All'}
    {country: 'Austria'},
    {country: 'Denmark'},
    {country: 'Finland'},
    {country: 'Germany'},
    {country: 'Liechenstein'},
    {country: 'Norway'},
    {country: 'Sweden'},
    {country: 'Switzerland'},
]

const parisCountries = [
    {country: 'All'}
    {country: 'Algeria'},
    {country: 'Basel Mulhouse'},
    {country: 'France'},
    {country: 'Morocco'},
    {country: 'Tunisia'},
]

const romeCountries = [
    {country: 'All'}
    {country: 'Italy'},
    {country: 'Malta'},
]

const madridCountries = [
    {country: 'All'}
    {country: 'Portugual'},
    {country: 'Spain'},
]

const albaniaCountries = [
    {country: 'All'}
    {country: 'Albania'},
    {country: 'Bosnia and Herzegovina'},
    {country: 'Kosovo'},
    {country: 'Macedonia'},
    {country: 'Montenegro'},
    {country: 'Serbia'},
]

const dublinCountries = [
    {country: 'Ireland'}
]
const allCountries = [
    {country: 'All'},
    {country: 'Albania'},
    {country: 'Algeria'},
    {country: 'Austria'},
    {country: 'Basel Mulhouse'},
    {country: 'Belarus'},
    {country: 'Belgium'},
    {country: 'Bosnia'},
    {country: 'Bulgaria'},
    {country: 'Croatia'},
    {country: 'Cyprus'},
    {country: 'Czech Republic'},
    {country: 'Denmark'},
    {country: 'Estonia'},
    {country: 'Finland'},
    {country: 'France'},
    {country: 'Germany'},
    {country: 'Greece'},
    {country: 'Iceland'},
    {country: 'Ireland'},
    {country: 'Italy'},
    {country: 'Kosovo'},
    {country: 'Latvia'},
    {country: 'Lithuania'},
    {country: 'Liechenstein'},
    {country: 'Luxembourg'},
    {country: 'Malta'},
    {country: 'Moldova'},
    {country: 'Montenegro'},
    {country: 'Morocco'},
    {country: 'Netherlands'},
    {country: 'Norway'},
    {country: 'North Macedonia'},
    {country: 'Poland'},
    {country: 'Portugual'},
    {country: 'Romania'},
    {country: 'Russia'},
    {country: 'Serbia'},
    {country: 'Slovakia'},
    {country: 'Slovenia'},
    {country: 'Spain'},
    {country: 'Switzerland'},
    {country: 'Sweden'},
    {country: 'Tunisia'},
    {country: 'Ukraine'}
];

let regionPosts = allPosts
let postCountries = allCountries
let timezones = nonLocalTimezones
