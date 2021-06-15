# drt-iei-dashboard
### Application is list the flights from countries in euro meds regions to UK for IEI  

#### Following is technical stack used in the project ... 

> Scala

> API - HTTP4s 

> Postgres - Skunk 

> Frontend - reactjs typescript materialUI




### Development stage

    - UI changes
        During development, UI change can retrieve backend data by adding `"proxy": "http://localhost:9001"` in package.json and make sure sbt server is running .

        > `yarn start` to start the UI server

        >  `CIRIUM_API_APPID=${APPID} \
            CIRIUM_API_APPKEY=${APPKEY} \
            sbt -J-Xmx4G -J-Xms4G -Duser.timezone=UTC run | tee iei-dashboard.log`
            
    - Backend-Frontend changes to run together on sbt server
        To include UI on jvm server change revert the above change `http://localhost:9001` and following commands 
        
        >  `yarn build` 
        
        >  `mv build ../src/main/resources/ui/`
        
        >  `CIRIUM_API_APPID=${APPID} \
            CIRIUM_API_APPKEY=${APPKEY} \
            sbt -J-Xmx4G -J-Xms4G -Duser.timezone=UTC run | tee iei-dashboard.log`
            
        > open browser and visit `http://localhost:9001/`