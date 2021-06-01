import React from 'react';
import { useQuery } from 'react-query';
import styled from 'styled-components';
import NavBar from './NavBar';
import Map from './Map';
import { useRecoilValue } from 'recoil';
import { filterDisplayAtom } from '../recoil/atom';

const SearchResult = () => {
  const contents = useRecoilValue(filterDisplayAtom);

  const miniSearchBar = () => {
    return <MiniSearchBarWrapper></MiniSearchBarWrapper>;
  };

  let foo = '';
  const { isLoading, error, data, isFetching } = useQuery('repoData', () =>
    fetch(
      'http://3.35.85.246:8080/api/accommodation/list/location?x=127.073&y=36.8037190756251&range=4&startDate=2021-01-01&endDate=2021-01-07&minFee=0&maxFee=110000&person=3'
    ).then(res => res.json())
  );

  if (isLoading) foo = 'Loading...';

  if (error) foo = 'An error has occurred: ';

  return (
    <div>
      <NavBarWrapper>
        <NavBar />
      </NavBarWrapper>
      <ListsWrapper></ListsWrapper>
      <MapWrapper>
        <Map />
      </MapWrapper>
    </div>
  );
};

const NavBarWrapper = styled.div`
  background-color: white;
  box-shadow: 0px 0px 4px rgba(204, 204, 204, 0.5),
    0px 2px 4px rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(4px);
`;

const MapWrapper = styled.div`
  position: absolute;
  right: 0px;
  top: 103px;
  z-index: -1;
`;

const MiniSearchBarWrapper = styled.div`
  /* Auto Layout */

  display: flex;

  align-items: center;
  padding: 8px 8px 8px 24px;

  position: absolute;
  left: 35.76%;
  right: 35.76%;
  top: 24.47%;
  bottom: 24.47%;

  /* White */

  background: #ffffff;
  /* Gray 4 */

  border: 1px solid #bdbdbd;
  box-sizing: border-box;
  border-radius: 30px;
`;

const ListsWrapper = styled.div`
  width: 684px;
  height: 200px;

  margin: 24px 0px;
`;

export default SearchResult;
