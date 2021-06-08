import React from 'react';
import DateFnsUtils from '@date-io/date-fns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import SearchFilters from './SearchFilters';
import Grid from '@material-ui/core/Grid';
import format from 'date-fns/format';

interface IProps {
}

interface IState {
    selectedDate?: Date
}

export default class MaterialUIPickers extends React.Component<IProps, IState> {

    handleDateChange = (sDate: Date | null) => {
        let selectedDate = sDate as Date
        this.setState({...this.state, selectedDate: selectedDate});
    };

    constructor(props: IProps) {
        super(props);
        this.state = {
            selectedDate: new Date()
        };
    }

    componentDidMount() {
        console.log("this.state.selectedDate" + this.state.selectedDate)
    }

    render() {
        return (
            <div style={{width: 1100}}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <MuiPickersUtilsProvider utils={DateFnsUtils}>
                            <KeyboardDatePicker
                                margin="normal"
                                id="date-picker-dialog"
                                label="Flight departure date"
                                format="yyyy-MM-dd"
                                value={this.state.selectedDate}
                                onChange={this.handleDateChange}
                                KeyboardButtonProps={{
                                    'aria-label': 'change date',
                                }}
                            />
                        </MuiPickersUtilsProvider>
                    </Grid>
                </Grid>
                <SearchFilters pickedDate={format(this.state.selectedDate as Date, "yyyy-MM-dd")}/>
            </div>
        );
    }
}
