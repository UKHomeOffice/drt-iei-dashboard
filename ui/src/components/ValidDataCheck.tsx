
function isNotValidMiddleEastPost(post: string) : boolean   {
        switch (post) {
            case 'All':
                return false;
            case 'Doha':
                 return false;
            case 'Dubai':
                return false;
            case 'Istanbul':
                return false;
            default:
                return true;
        }
 };

 function isNotValidEuromedNorthPost(post: string) : boolean {
        switch (post) {
            case 'All':
                return false;
            case 'Benelux':
                 return false;
            case 'Berlin':
                return false;
            case 'Paris':
                return false;
            case 'Warsaw':
                return false;
            default:
                return true;
        }
 }

  function isNotValidEuromedSouthPost(post: string) : boolean {
         switch (post) {
             case 'All':
                 return false;
             case 'Athens':
                  return false;
             case 'Albania':
                 return false;
             case 'Dublin':
                 return false;
             case 'Madrid':
                 return false;
             case 'Rome':
                 return false;
             default:
                 return true;
         }
  }

export function isValidRequest(region: string , post : string) : boolean {

        console.log('isValidRequest region = ' + region + ' post = ' + post);
        if(region === 'China' && post !== 'Guangdong Province') {
            return false;
        } else if(region === 'India' && post !== 'New Delhi') {
            return false;
        } else if(region === 'Africa' && post !== 'Lagos') {
            return false;
        } else if(region === 'Middle East' && isNotValidMiddleEastPost(post)){
            return false;
        } else if(region === 'Euromed North' && isNotValidEuromedNorthPost(post)){
            return false;
        } else if(region === 'Euromed South' && isNotValidEuromedSouthPost(post)){
            return false;
        } else {
            return true;
        }
    }