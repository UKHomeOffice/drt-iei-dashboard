import {
    asiaPacificPost,
    africaPost,
    euromedNorthPost,
    euromedSouthPost,
    middleEastAndPakistanPost,
    southAndSoutheastAsiaPost,
    westernBalkansPost
} from "./RegionPortData";

function isNotValidAfricaPost(post: string): boolean {
    return africaPost.filter(item => item.post === post).length === 0;
}

function isNotValidMiddleEastPost(post: string): boolean {
    return middleEastAndPakistanPost.filter(item => item.post === post).length === 0;
};

function isNotValidEuromedNorthPost(post: string): boolean {
    return euromedNorthPost.filter(item => item.post === post).length === 0;
}

function isNotValidEuromedSouthPost(post: string): boolean {
    return euromedSouthPost.filter(item => item.post === post).length === 0;
}

function isNotValidAsiaPacificPost(post: string): boolean {
    return asiaPacificPost.filter(item => item.post === post).length === 0;
}

function isNotValidSouthAndSoutheastAsiaPost(post: string): boolean {
    return southAndSoutheastAsiaPost.filter(item => item.post === post).length === 0;
}

function isNotValidWesternBalkansPost(post: string): boolean {
    return westernBalkansPost.filter(item => item.post === post).length === 0;
}

export function isValidRequest(region: string, post: string): boolean {

    console.log('isValidRequest region = ' + region + ' post = ' + post);
    if (region === 'Asia Pacific' && isNotValidAsiaPacificPost(post)) {
        return false;
    } else if (region === 'Africa' && isNotValidAfricaPost(post)) {
        return false;
    } else if (region === 'Middle East and Pakistan' && isNotValidMiddleEastPost(post)) {
        return false;
    } else if (region === 'Euromed North' && isNotValidEuromedNorthPost(post)) {
        return false;
    } else if (region === 'Euromed South' && isNotValidEuromedSouthPost(post)) {
        return false;
    } else if (region === 'Western Balkans' && isNotValidWesternBalkansPost(post)) {
        return false;
    } else if (region === 'South and South East Asia' && isNotValidSouthAndSoutheastAsiaPost(post)) {
        return false;
    } else {
        return true;
    }
}