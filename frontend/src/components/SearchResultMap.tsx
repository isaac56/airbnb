import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Icons from './Icons';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import useFetch from './UseFetch';
import { useRecoilValue, useRecoilState } from 'recoil';
import { checkInOutData, priceData, guestsData, mapData } from '../recoil/atom';

declare global {
  interface Window {
    kakao: any;
  }
}

const SearchResultMap = markerPositions => {
  const [kakaoMap, setKakaoMap] = useState<any | null>(null);
  const [checked, setChecked] = React.useState(true);
  const [markers, setMarkers] = useState([]);
  const [center, setCenter] = useState({ lat: null, lng: null });

  const dates = useRecoilValue(checkInOutData);
  const prices = useRecoilValue(priceData);
  const guests = useRecoilValue(guestsData);
  const [maps, setMaps] = useRecoilState(mapData);

  const { isLoading, data, error, isFetching } = useFetch('latLngFilter', {
    checkIn: dates.체크인,
    checkOut: dates.체크아웃,
    minPrice: prices.최저가,
    maxPrice: prices.최고가,
    guests: Object.values(guests).reduce((a, b) => (a || 0) + (b || 0), 0),
    lat: center.lat,
    lng: center.lng,
  });
  setMaps(data);
  useEffect(() => {
    let container = document.getElementById('map');
    let options = {
      center: new window.kakao.maps.LatLng(
        markerPositions.markerPositions[0].lat,
        markerPositions.markerPositions[0].lng
      ),
      level: 3,
    };

    let map = new window.kakao.maps.Map(container, options);
    setKakaoMap(map);
    setMarkers(markerPositions.markerPositions);
    window.kakao.maps.event.addListener(map, 'center_changed', function () {
      const latlng = map.getCenter();
      setCenter({ ...center, lat: latlng.getLat(), lng: latlng.getLng() });
    });
  }, []);

  useEffect(() => {
    markers.forEach((latLng: any) => {
      // 마커를 생성합니다
      new window.kakao.maps.Marker({
        //마커가 표시 될 지도
        map: kakaoMap,
        //마커가 표시 될 위치
        position: new window.kakao.maps.LatLng(latLng.lat, latLng.lng),
        //마커에 hover시 나타날 title
        title: latLng.title,
      });
    });
  }, [markers]);

  function zoomIn() {
    kakaoMap.setLevel(kakaoMap.getLevel() - 1);
  }

  function zoomOut() {
    kakaoMap.setLevel(kakaoMap.getLevel() + 1);
  }

  function setDraggable(draggable: boolean = true) {
    kakaoMap.setDraggable(draggable);
  }

  const handleChange = event => {
    setDraggable(event.target.checked);
    setChecked(event.target.checked);
  };

  return (
    <>
      <Div>
        <div id="map" style={{ width: '50vw', height: '1024px' }} />
      </Div>
      <CheckBoxWrapper>
        <FormControlLabel
          control={
            <Checkbox
              checked={checked}
              onChange={handleChange}
              inputProps={{ 'aria-label': 'primary checkbox' }}
              style={{
                transform: 'scale(2)',
              }}
            />
          }
          label={
            <span
              style={{
                fontSize: '1.2rem',
                color: '#0a3174',
                fontWeight: 'bold',
              }}>
              지도를 움직이며 검색하기
            </span>
          }
        />
      </CheckBoxWrapper>
      <ZoomControlWrapper>
        <div onClick={zoomIn}>
          <Icons type="plus" />
        </div>
        <div onClick={zoomOut}>
          <Icons type="minus" />
        </div>
      </ZoomControlWrapper>
    </>
  );
};

const Div = styled.div``;

const ZoomControlWrapper = styled.div`
  position: absolute;
  right: 30px;
  top: 45px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 8px;
  cursor: pointer;
  /* White */

  background: #ffffff;
  /* up2 */

  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 8px;
  z-index: 999;
`;

const CheckBoxWrapper = styled.div`
  position: absolute;
  background-color: 'white';
  right: 35%;
  top: 3%;
  z-index: 100;
`;

const CheckBox = styled.label`
  display: inline-flex;
  cursor: pointer;
`;

export default SearchResultMap;
