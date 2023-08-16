import moment from "moment";
export function formatDateTime(dateTimeString) {


    const dttm = moment(dateTimeString).format("MM월 DD일 HH시 MM분");

    return dttm;
    }
  