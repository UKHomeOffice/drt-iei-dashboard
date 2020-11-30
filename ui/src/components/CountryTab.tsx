import React from 'react';
import { makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import ArrivalFlightDataTable from './DataTable'

interface TabPanelProps {
  children?: React.ReactNode;
  index: any;
  value: any;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: any) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

const useStyles = makeStyles((theme: Theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    width:1100
  },
}));

export default function SimpleTabs() {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Tabs value={value} onChange={handleChange} aria-label="simple tabs example">
          <Tab label="Greece" {...a11yProps(0)} />
          <Tab label="Cyprus" {...a11yProps(1)} />
          <Tab label="Croatia" {...a11yProps(2)} />
          <Tab label="Slovenia" {...a11yProps(3)} />
          <Tab label="Bulgaria" {...a11yProps(4)} />
          <Tab label="Romania" {...a11yProps(5)} />
          <Tab label="Moldova" {...a11yProps(6)} />
        </Tabs>
      </AppBar>
      <TabPanel value={value} index={0}>
       <ArrivalFlightDataTable region = "athens" country = "Greece" date = "2018-12-21"/>
      </TabPanel>
      <TabPanel value={value} index={1}>
       <ArrivalFlightDataTable region = "athens" country = "Cyprus" date = "2018-12-21"/>
      </TabPanel>
      <TabPanel value={value} index={2}>
       <ArrivalFlightDataTable region = "athens" country = "Croatia" date = "2018-12-21"/>
      </TabPanel>
      <TabPanel value={value} index={3}>
       <ArrivalFlightDataTable region = "athens" country = "Slovenia" date = "2018-12-21"/>
      </TabPanel>
      <TabPanel value={value} index={4}>
       <ArrivalFlightDataTable region = "athens" country = "Bulgaria" date = "2018-12-21"/>
      </TabPanel>
      <TabPanel value={value} index={5}>
       <ArrivalFlightDataTable region = "athens" country = "Romania" date = "2018-12-21"/>
      </TabPanel>
      <TabPanel value={value} index={6}>
       <ArrivalFlightDataTable region = "athens" country = "Moldova" date = "2018-12-21"/>
      </TabPanel>
    </div>
  );
}