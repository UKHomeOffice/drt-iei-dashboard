import React from 'react';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import FlightsTable from './FlightsTable'
import Grid from '@material-ui/core/Grid';
import { regions, allTimezones, nonLocalTimezones, euromedNorthPost, euromedSouthPost, chinaPost, indiaPost,
middleEastPost ,africaPost, allPosts, athensCountries, beneluxCountries, warsawCountries, berlinCountries,
parisCountries, romeCountries, madridCountries, albaniaCountries, guangdongProvinceCountries, newDelhiCountries,
dubaiCountries, dublinCountries, istanbulCountries, dohaCountries, africaCountries, allCountries } from "./RegionPortData";

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
        console.log("this.state.region " + this.state.region);
        if (this.state !== prevState && this.state.region !== prevState.region) {
            this.setState({timezone: 'UTC'});
            switch (this.state.region) {
                case 'Africa' :
                    this.setState({post: 'Lagos'});
                    regionPosts = africaPost;
                    console.log("It is a africaPost.");
                    break;
                case 'China' :
                    this.setState({post: 'Guangdong Province'});
                    regionPosts = chinaPost;
                    console.log("It is a chinaPost.");
                    break;
                case 'Euromed North' :
                    this.setState({post: 'All'});
                    regionPosts = euromedNorthPost;
                    console.log("It is a euromedNorthPost.");
                    break;
                case 'Euromed South' :
                    this.setState({post: 'All'});
                    regionPosts = euromedSouthPost;
                    console.log("It is a euromedSouthPost.");
                    break;
                case 'India' :
                    this.setState({post: 'New Delhi'});
                    regionPosts = indiaPost;
                    console.log("It is a indiaPost.");
                    break;
                case 'Middle East' :
                    this.setState({post: 'All'});
                    regionPosts = middleEastPost;
                    console.log("It is a istanbulPost.");
                    break;
                case 'All' :
                    this.setState({post: 'All'});
                    regionPosts = allPosts;
                    console.log("It is a allPosts.");
                    break;
            }
        }

        if (this.state !== prevState && this.state.post !== prevState.post) {
           console.log("this.state.post " + this.state.post);
            this.setState({country: 'All'});
            this.setState({timezone: 'UTC'});
            switch (this.state.post) {
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
                case 'Lagos':
                    postCountries = africaCountries;
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

let regionPosts = allPosts
let postCountries = allCountries
let timezones = nonLocalTimezones
