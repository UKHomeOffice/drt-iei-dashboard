
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
            case 'Islamabad':
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

   function isNotValidAsiaPacificPost(post: string) : boolean {
           switch (post) {
               case 'All':
                   return false;
               case 'Beijing':
                    return false;
               case 'Bangkok':
                   return false;
               default:
                   return true;
           }
    }

       function isNotValidSouthAndSouthEastAsiaPost(post: string) : boolean {
               switch (post) {
                   case 'All':
                       return false;
                   case 'Hanoi':
                        return false;
                   case 'New Delhi':
                       return false;
                   default:
                       return true;
               }
        }

export function isValidRequest(region: string , post : string) : boolean {

        console.log('isValidRequest region = ' + region + ' post = ' + post);
        if(region === 'Asia Pacific' && isNotValidAsiaPacificPost(post)) {
            return false;
        } else if(region === 'Africa' && post !== 'Lagos') {
            return false;
        } else if(region === 'Middle East and Pakistan' && isNotValidMiddleEastPost(post)){
            return false;
        } else if(region === 'Euromed North' && isNotValidEuromedNorthPost(post)){
            return false;
        } else if(region === 'Euromed South' && isNotValidEuromedSouthPost(post)){
            return false;
        } else if(region === 'Western Balkans' && post !== 'Tirana') {
            return false;
        } else if(region === 'South and South East Asia' && isNotValidSouthAndSouthEastAsiaPost(post)) {
            return false;
        } else {
            return true;
        }
    }