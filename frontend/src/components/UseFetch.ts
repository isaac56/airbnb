import { useQuery } from 'react-query';
import axios from 'axios';

interface Ifilters {
  checkIn: any;
  checkOut: any;
  price: number | null;
  guests: number | null;
}

const getHomesByFilter = async (filters: Ifilters) => {
  const { data } = await axios.get(
    `http://3.35.85.246:8080/api/accommodation/list/location?x=127.073&y=36.8037190756251&range=4&startDate=${
      filters.checkIn.toISOString().split('T')[0]
    }&endDate=${
      filters.checkOut.toISOString().split('T')[0]
    }&minFee=&maxFee=&person=${filters.guests}`
  );
  return data;
};

export default function useFetch(filters: Ifilters) {
  return useQuery(['getHomes', filters], () => getHomesByFilter(filters));
}
