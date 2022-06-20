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
                case 'Africa' :
                    regionPosts = africaPost;
                    console.log("It is a africaPost.");
                    break;
                case 'China' :
                    regionPosts = chinaPost;
                    console.log("It is a chinaPost.");
                    break;
                case 'Doha' :
                    regionPosts = dohaPost;
                    console.log("It is a dohaPost.");
                    break;
                case 'Dubai' :
                    regionPosts = dubaiPost;
                    console.log("It is a dubaiPost.");
                    break;
                case 'Euromed North' :
                    regionPosts = euromedNorthPost;
                    console.log("It is a euromedNorthPost.");
                    break;
                case 'Euromed South' :
                    regionPosts = euromedSouthPost;
                    console.log("It is a euromedSouthPost.");
                    break;
                case 'India' :
                    regionPosts = indiaPost;
                    console.log("It is a indiaPost.");
                    break;
                case 'Istanbul' :
                    regionPosts = istanbulPost;
                    console.log("It is a istanbulPost.");
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
                case 'Africa':
                    postCountries = africaCountries;
                    break;
                case 'Albania':
                    postCountries = albaniaCountries;
                    break;
                case 'All':
                    postCountries = allCountries;
                    break;
                case 'Athens' :
                    postCountries = athensCountries;
                    break;
                case 'Benelux':
                    postCountries = beneluxCountries;
                    break;
                case 'Berlin':
                    postCountries = berlinCountries;
                    break;
                case 'Dublin':
                    postCountries = dublinCountries;
                    break;
                case 'Doha':
                    postCountries = dohaCountries;
                    break;
                case 'Dubai':
                    postCountries = dubaiCountries;
                    break;
                case 'Guangdong Province':
                    postCountries = guangdongProvinceCountries;
                    break;
                case 'Istanbul':
                    postCountries = istanbulCountries;
                    break;
                case 'Madrid':
                    postCountries = madridCountries;
                    break;
                case 'New Delhi':
                    postCountries = newDelhiCountries;
                    break;
                case 'Paris':
                    postCountries = parisCountries;
                    break;
                case 'Rome':
                    postCountries = romeCountries;
                    break;
                case 'Warsaw':
                    postCountries = warsawCountries;
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
    {region: 'All'},
    {region: 'Africa'},
    {region: 'China'},
    {region: 'Doha'},
    {region: 'Dubai'},
    {region: 'Euromed North'},
    {region: 'Euromed South'},
    {region: 'India'},
    {region: 'Istanbul'}

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
    {post: 'All'},
    {post: 'Benelux'},
    {post: 'Berlin'},
    {post: 'Paris'},
    {post: 'Warsaw'}
]

const euromedSouthPost = [
    {post: 'All'},
    {post: 'Athens'},
    {post: 'Albania'},
    {post: 'Dublin'},
    {post: 'Madrid'},
    {post: 'Rome'}
]

const chinaPost = [
    {post: 'All'},
    {post: 'Guangdong Province'}
]

const indiaPost = [
    {post: 'All'},
    {post: 'New Delhi'}
]

const dubaiPost = [
    {post: 'All'},
    {post: 'Dubai'}
]

const istanbulPost = [
    {post: 'All'},
    {post: 'Istanbul'}
]

const dohaPost = [
    {post: 'All'},
    {post: 'Doha'}
]

const africaPost = [
    {post: 'All'},
    {post: 'Africa'}
]

const allPosts = [
    {post: 'All'},
    {post: 'Africa'},
    {post: 'Albania'},
    {post: 'Athens'},
    {post: 'Benelux'},
    {post: 'Berlin'},
    {post: 'Doha'},
    {post: 'Dublin'},
    {post: 'Dubai'},
    {post: 'Istanbul'},
    {post: 'Guangdong Province'},
    {post: 'Madrid'},
    {post: 'New Delhi'},
    {post: 'Paris'},
    {post: 'Rome'},
    {post: 'Warsaw'}
]

const athensCountries = [
    {country: 'All'},
    {country: 'Bulgaria'},
    {country: 'Croatia'},
    {country: 'Cyprus'},
    {country: 'Greece'},
    {country: 'Moldova'},
    {country: 'Romania'},
    {country: 'Slovenia'}
]

const beneluxCountries = [
    {country: 'All'},
    {country: 'Belgium'},
    {country: 'Luxembourg'},
    {country: 'Netherlands'}
]

const warsawCountries = [
    {country: 'All'},
    {country: 'Belarus'},
    {country: 'Czech Republic'},
    {country: 'Estonia'},
    {country: 'Iceland'},
    {country: 'Latvia'},
    {country: 'Lithuania'},
    {country: 'Poland'},
    {country: 'Russia'},
    {country: 'Slovakia'},
    {country: 'Ukraine'}
]

const berlinCountries = [
    {country: 'All'},
    {country: 'Austria'},
    {country: 'Denmark'},
    {country: 'Finland'},
    {country: 'Germany'},
    {country: 'Norway'},
    {country: 'Sweden'},
    {country: 'Switzerland'}
]

const parisCountries = [
    {country: 'All'},
    {country: 'Algeria'},
    {country: 'France'},
    {country: 'Morocco'},
    {country: 'Tunisia'}
]

const romeCountries = [
    {country: 'All'},
    {country: 'Italy'},
    {country: 'Malta'}
]

const madridCountries = [
    {country: 'All'},
    {country: 'Portugal'},
    {country: 'Spain'}
]

const albaniaCountries = [
    {country: 'All'},
    {country: 'Albania'},
    {country: 'Bosnia and Herzegovina'},
    {country: 'Kosovo'},
    {country: 'Macedonia'},
    {country: 'Montenegro'},
    {country: 'Serbia'}
]

const guangdongProvinceCountries = [
    {country: 'All'},
    {country: 'Australia'},
    {country: 'Brunei'},
    {country: 'China'},
    {country: 'Fiji'},
    {country: 'Hong Kong'},
    {country: 'Japan'},
    {country: 'Macau'},
    {country: 'Mongolia'},
    {country: 'New Zealand'},
    {country: 'North Korea'},
    {country: 'Philippines'},
    {country: 'South Korea'},
    {country: 'Taiwan'}
]

const newDelhiCountries = [
    {country: 'All'},
    {country: 'Afghanistan'},
    {country: 'Bhutan'},
    {country: 'Burma'},
    {country: 'Cambodia'},
    {country: 'India'},
    {country: 'Laos'},
    {country: 'Myanmar'},
    {country: 'Thailand'},
    {country: 'Vietnam'}
]

const dubaiCountries = [
    {country: 'Afghanistan'},
    {country: 'Oman'},
    {country: 'United Arab Emirates'},
    {country: 'Yemen'}
]

const dublinCountries = [
    {country: 'Ireland'}
]

const istanbulCountries = [
    {country: 'Armenia'},
    {country: 'Azerbaijan'},
    {country: 'Georgia'},
    {country: 'Kazakhstan'},
    {country: 'Lebanon'},
    {country: 'Jordan'},
    {country: 'Iraq'},
    {country: 'Uzbekistan'},
    {country: 'Turkmenistan'},
    {country: 'Turkey'},
    {country: 'Syria'}
]

const dohaCountries = [
    {country: 'Bahrain'},
    {country: 'Kuwait'},
    {country: 'Qatar'},
    {country: 'Saudi Arabia'}
]

const africaCountries = [
    {country: 'Cameroon'},
    {country: 'Central African Republic'},
    {country: 'Equatorial Guinea'} ,
    {country: 'Nigeria'}
]

const allCountries = [
    {country: 'All'},
    {country: 'Albania'},
    {country: 'Algeria'},
    {country: 'Afghanistan'},
    {country: 'Armenia'},
    {country: 'Austria'},
    {country: 'Australia'},
    {country: 'Azerbaijan'},
    {country: 'Bahrain'},
    {country: 'Belarus'},
    {country: 'Belgium'},
    {country: 'Bhutan'},
    {country: 'Bosnia'},
    {country: 'Bulgaria'},
    {country: 'Brunei'},
    {country: 'Burma'},
    {country: 'Cambodia'},
    {country: 'Cameroon'},
    {country: 'Central African Republic'},
    {country: 'China'},
    {country: 'Croatia'},
    {country: 'Cyprus'},
    {country: 'Czech Republic'},
    {country: 'Denmark'},
    {country: 'Equatorial Guinea'} ,
    {country: 'Estonia'},
    {country: 'Finland'},
    {country: 'Fiji'},
    {country: 'France'},
    {country: 'Georgia'},
    {country: 'Germany'},
    {country: 'Greece'},
    {country: 'Hong Kong'},
    {country: 'Iceland'},
    {country: 'Ireland'},
    {country: 'Italy'},
    {country: 'Iraq'},
    {country: 'Japan'},
    {country: 'Jordan'},
    {country: 'Kazakhstan'},
    {country: 'Kosovo'},
    {country: 'Kuwait'},
    {country: 'Laos'},
    {country: 'Latvia'},
    {country: 'Lebanon'},
    {country: 'Lithuania'},
    {country: 'Luxembourg'},
    {country: 'Macau'},
    {country: 'Malta'},
    {country: 'Moldova'},
    {country: 'Montenegro'},
    {country: 'Morocco'},
    {country: 'Mongolia'},
    {country: 'Myanmar'},
    {country: 'Nigeria'},
    {country: 'New Zealand'},
    {country: 'Netherlands'},
    {country: 'North Korea'},
    {country: 'Norway'},
    {country: 'North Macedonia'},
    {country: 'Oman'},
    {country: 'Philippines'},
    {country: 'Poland'},
    {country: 'Portugal'},
    {country: 'Qatar'},
    {country: 'Romania'},
    {country: 'Russia'},
    {country: 'Serbia'},
    {country: 'Slovakia'},
    {country: 'Slovenia'},
    {country: 'Spain'},
    {country: 'Switzerland'},
    {country: 'Sweden'},
    {country: 'Tunisia'},
    {country: 'Taiwan'},
    {country: 'Thailand'},
    {country: 'Turkey'},
    {country: 'Turkmenistan'},
    {country: 'Saudi Arabia'},
    {country: 'South Korea'},
    {country: 'Syria'},
    {country: 'Ukraine'},
    {country: 'United Arab Emirates'},
    {country: 'Uzbekistan'},
    {country: 'Vietnam'},
    {country: 'Yemen'},
   ]

let regionPosts = allPosts
let postCountries = allCountries
let timezones = nonLocalTimezones
