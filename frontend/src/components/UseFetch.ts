import { useQuery } from 'react-query';
import axios from 'axios';

interface Ifilters {
  checkIn?: any;
  checkOut?: any;
  minPrice?: number | null;
  maxPrice?: number | null;
  guests?: number | null;
  lat?: number | null;
  lng?: number | null;
}

const url = 'http://3.35.85.246:8080/api/accommodation/list/';

const getHomesByFilter = async (type, filters: Ifilters) => {
  switch (type) {
    case 'userFilter':
      const dataByUserFilter = await axios.get(
        `${url}region?regionDepth1=&startDate=${
          filters.checkIn.toISOString().split('T')[0]
        }&endDate=${filters.checkOut.toISOString().split('T')[0]}&minFee=${
          filters.minPrice || ''
        }&maxFee=${filters.maxPrice || ''}&person=${filters.guests || ''}`
      );
      return dataByUserFilter.data.data;

    case 'latLngFilter':
      const dataByLatLng = await axios.get(
        `${url}location?x=${filters.lng}&y=${filters.lat}&range=4&startDate=${
          filters.checkIn.toISOString().split('T')[0]
        }&endDate=${filters.checkOut.toISOString().split('T')[0]}&minFee=${
          filters.minPrice || ''
        }&maxFee=${filters.maxPrice || ''}&person=${filters.guests || ''}`
      );
      return dataByLatLng.data.data;

    case 'login':
      const login = await axios.get(
        'http://3.35.85.246:8080/api/user/login/oauth/github?code='
      );
      return login;
  }
};

export default function useFetch(type, filters) {
  return useQuery(['getHomes', filters], () => getHomesByFilter(type, filters));
}
