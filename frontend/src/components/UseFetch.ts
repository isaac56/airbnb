import { useQuery } from 'react-query';
import axios from 'axios';

interface Ifilters {
  checkIn?: any;
  checkOut?: any;
  minPrice?: number | null;
  maxPrice?: number | null;
  guests?: number | null;
}

const getHomesByFilter = async (filters: Ifilters) => {
  const url = 'http://3.35.85.246:8080/api/accommodation/list/';

  const { data } = await axios.get(
    `${url}region?regionDepth1=&startDate=${
      filters.checkIn.toISOString().split('T')[0]
    }&endDate=${filters.checkOut.toISOString().split('T')[0]}&minFee=${
      filters.minPrice || ''
    }&maxFee=${filters.maxPrice || ''}&person=${filters.guests || ''}`
  );

  return data.data;
};

export default function useFetch(filters: Ifilters) {
  return useQuery(['getHomes', filters], () => getHomesByFilter(filters));
}
