export const regions = [
    {region: 'All'},
    {region: 'Africa'},
    {region: 'Asia Pacific'},
    {region: 'Euromed North'},
    {region: 'Euromed South'},
    {region: 'Middle East and Pakistan'},
    {region: 'South and South East Asia'},
    {region: 'Western Balkans'}
]

export const allTimezones = [
    {timezone: 'UK'},
    {timezone: 'UTC'},
    {timezone: 'Local'}
]

export const nonLocalTimezones = [
    {timezone: 'UK'},
    {timezone: 'UTC'}
]

export const asiaPacificPost = [
    {post: 'Beijing'}
]

export const euromedNorthPost = [
    {post: 'All'},
    {post: 'Benelux'},
    {post: 'Berlin'},
    {post: 'Bucharest'},
    {post: 'Paris'},
    {post: 'Warsaw'},
]

export const euromedSouthPost = [
    {post: 'All'},
    {post: 'Athens'},
    {post: 'Dublin'},
    {post: 'Madrid'},
    {post: 'Rome'}
]

export const westernBalkansPost = [
    {post: 'Tirana'},
]

export const middleEastAndPakistanPost = [
    {post: 'All'},
    {post: 'Doha'},
    {post: 'Dubai'},
    {post: 'Istanbul'},
    {post: 'Islamabad'}
]

export const southAndSouthEastAsiaPost = [
    {post: 'All'},
    {post: 'Bangkok'},
    {post: 'Hanoi'},
    {post: 'Colombo'},
    {post: 'Dhaka'},
    {post: 'New Delhi'}
]
export const africaPost = [
    {post: 'Lagos'}
]

export const allPosts = [
    {post: 'All'},
    {post: 'Athens'},
    {post: 'Bangkok'},
    {post: 'Benelux'},
    {post: 'Beijing'},
    {post: 'Berlin'},
    {post: 'Bucharest'},
    {post: 'Colombo'},
    {post: 'Dhaka'},
    {post: 'Doha'},
    {post: 'Dublin'},
    {post: 'Dubai'},
    {post: 'Hanoi'},
    {post: 'Islamabad'},
    {post: 'Istanbul'},
    {post: 'Lagos'},
    {post: 'Madrid'},
    {post: 'New Delhi'},
    {post: 'Paris'},
    {post: 'Rome'},
    {post: 'Tirana'},
    {post: 'Warsaw'}
]

export const athensCountries = [
    {country: 'All'},
    {country: 'Cyprus'},
    {country: 'Greece'}
]

export const bucharestCountries = [
    {country: 'All'},
    {country: 'Bulgaria'},
    {country: 'Hungary'},
    {country: 'Moldova'},
    {country: 'Romania'},
    {country: 'Slovakia'}
]

export const beneluxCountries = [
    {country: 'All'},
    {country: 'Belgium'},
    {country: 'Luxembourg'},
    {country: 'Netherlands'}
]

export const warsawCountries = [
    {country: 'All'},
    {country: 'Belarus'},
    {country: 'Czech Republic'},
    {country: 'Estonia'},
    {country: 'Iceland'},
    {country: 'Latvia'},
    {country: 'Lithuania'},
    {country: 'Poland'},
    {country: 'Russia'},
    {country: 'Ukraine'}
]

export const berlinCountries = [
    {country: 'All'},
    {country: 'Austria'},
    {country: 'Denmark'},
    {country: 'Finland'},
    {country: 'Germany'},
    {country: 'Norway'},
    {country: 'Sweden'},
    {country: 'Switzerland'}
]

export const colomboCountries = [
    {country: 'Sri Lanka'},
]

export const dhakaCountries = [
    {country: 'Bangladesh'}
]
export const parisCountries = [
    {country: 'All'},
    {country: 'Algeria'},
    {country: 'France'},
    {country: 'Morocco'},
    {country: 'Tunisia'}
]

export const romeCountries = [
    {country: 'All'},
    {country: 'Italy'},
    {country: 'Malta'},
    {country: 'Slovenia'}
]

export const madridCountries = [
    {country: 'All'},
    {country: 'Portugal'},
    {country: 'Spain'}
]

export const tiranaCountries = [
    {country: 'All'},
    {country: 'Albania'},
    {country: 'Bosnia and Herzegovina'},
    {country: 'Croatia'},
    {country: 'Kosovo'},
    {country: 'Macedonia'},
    {country: 'Montenegro'},
    {country: 'Serbia'}
]

export const bangkokCountries = [
    {country: 'All'},
    {country: 'Cambodia'},
    {country: 'Myanmar'},
    {country: 'Thailand'}
]

export const beijingCountries = [
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

export const newDelhiCountries = [
    {country: 'All'},
    {country: 'Afghanistan'},
    {country: 'Bhutan'},
    {country: 'Burma'},
    {country: 'India'},
    {country: 'Nepal'}
]

export const dubaiCountries = [
    {country: 'All'},
    {country: 'Afghanistan'},
    {country: 'Iran'},
    {country: 'Oman'},
    {country: 'United Arab Emirates'},
    {country: 'Yemen'}
]

export const dublinCountries = [
    {country: 'Ireland'}
]

export const hanoiCountries = [
    {country: 'All'},
    {country: 'Laos'},
    {country: 'Vietnam'}
]

export const istanbulCountries = [
    {country: 'All'},
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

export const islamabadCountries = [
    {country: 'Pakistan'}
]
export const dohaCountries = [
    {country: 'All'},
    {country: 'Bahrain'},
    {country: 'Kuwait'},
    {country: 'Qatar'},
    {country: 'Saudi Arabia'}
]

export const lagosCountries = [
    {country: 'All'},
    {country: 'Cameroon'},
    {country: 'Central African Republic'},
    {country: 'Equatorial Guinea'},
    {country: 'Nigeria'}
]

const allCombineCountries =
    Array.from(new Set(athensCountries
        .concat(bangkokCountries)
        .concat(beijingCountries)
        .concat(berlinCountries)
        .concat(bucharestCountries)
        .concat(beneluxCountries)
        .concat(colomboCountries)
        .concat(dhakaCountries)
        .concat(dohaCountries)
        .concat(dubaiCountries)
        .concat(dublinCountries)
        .concat(islamabadCountries)
        .concat(istanbulCountries)
        .concat(lagosCountries)
        .concat(parisCountries)
        .concat(madridCountries)
        .concat(newDelhiCountries)
        .concat(romeCountries)
        .concat(tiranaCountries)
        .concat(warsawCountries)))

const sortedAllCountries = allCombineCountries.sort((a, b) => (a.country > b.country) ? 1 : -1)

const hasCountriesDuplicated = () => {
    const duplicatedDeleteCountries = sortedAllCountries.filter((value, index) =>
        sortedAllCountries.findIndex(item => item.country === value.country) === index
    );
    return duplicatedDeleteCountries;
};

export const allCountries = hasCountriesDuplicated();
