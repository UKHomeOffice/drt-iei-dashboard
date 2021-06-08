# drt-iei-dashboard
### Application is list the flights from countries in euro meds regions to UK for IEI  

#### Following is technical stack used in the project ... 

> Scala

> API - HTTP4s 

> Postgres - Skunk 

> Frontend - reactjs typescript materialUI




### Development stage

    - UI changes
        During development, UI change can retrieve backend data by running command `yarn start` which needs changes in function flightsEndPoint of typescript file FlightsTable.tsx to point to localhost api server

        ```
            public flightsEndPoint(region: string, post: string, country: string, filterDate: string, timezone:string) {
                return "http://localhost:9001/flights/" + region + "/" + post + "/" + country + "/"+ filterDate + "/" + timezone
            }
        ```
    - Backend-Frontend changes to run
        To include UI change revert the above change`http://localhost:9001` and following commands 
        
        >  `yarn build` 
        
        > `mv build ../src/main/resources/ui/`
        
        >  `CIRIUM_API_APPID=${APPID} \
            CIRIUM_API_APPKEY=${APPKEY} \
            sbt -J-Xmx4G -J-Xms4G -Duser.timezone=UTC run | tee iei-dashboard.log`
            `
        > open browser and visit http://localhost:9001/