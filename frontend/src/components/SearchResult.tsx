import React from 'react';
import styled from 'styled-components';
import NavBar from './NavBar';
import SearchResultMap from './SearchResultMap';
import { useRecoilValue } from 'recoil';
import { checkInOutData, priceData, guestsData } from '../recoil/atom';
import useFetch from './UseFetch';

const SearchResult = () => {
  const dates = useRecoilValue(checkInOutData);
  const guests = useRecoilValue(guestsData);
  const prices = useRecoilValue(priceData);

  let foo = '';
  // const miniSearchBar = () => {
  //   return <MiniSearchBarWrapper></MiniSearchBarWrapper>;
  // };

  const { isLoading, data, error } = useFetch({
    checkIn: dates.체크인,
    checkOut: dates.체크아웃,
    minPrice: prices.최저가,
    maxPrice: prices.최고가,
    guests: Object.values(guests).reduce((a, b) => (a || 0) + (b || 0), 0),
  });

  if (isLoading) foo = 'Loading...';

  if (error) foo = 'An error has occurred: ';

  return (
    <div>
      <NavBarWrapper>
        <NavBar />
      </NavBarWrapper>
      <div>
        <ListsWrapper>
          {data &&
            data.map((v, i) => {
              return <p key={i}>{v.id}</p>;
            })}
        </ListsWrapper>
        <MapWrapper>
          <SearchResultMap />
        </MapWrapper>
      </div>
    </div>
  );
};

const NavBarWrapper = styled.div`
  background-color: white;
  box-shadow: 0px 0px 4px rgba(204, 204, 204, 0.5),
    0px 2px 4px rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(4px);
  height: 200px;
`;

const MapWrapper = styled.div`
  position: absolute;
  right: 0px;
  top: 103px;
  z-index: -1;
`;

const MiniSearchBarWrapper = styled.div`
  //supposed to modify...
  display: flex;

  align-items: center;
  padding: 8px 8px 8px 24px;

  position: absolute;
  left: 35.76%;
  right: 35.76%;
  top: 24.47%;
  bottom: 24.47%;

  background: #ffffff;
  /* Gray 4 */

  border: 1px solid #bdbdbd;
  box-sizing: border-box;
  border-radius: 30px;
`;

const ListsWrapper = styled.div`
  width: 50%;
  height: 20%;

  margin: 24px 0px;
`;

export default SearchResult;
