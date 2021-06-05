import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import NavBar from './NavBar';
import SearchResultMap from './SearchResultMap';
import { useRecoilValue, useRecoilState } from 'recoil';
import { checkInOutData, priceData, guestsData, mapData } from '../recoil/atom';
import CircularProgress from '@material-ui/core/CircularProgress';
import { makeStyles } from '@material-ui/core/styles';
import useFetch from './UseFetch';

const SearchResult = () => {
  const dates = useRecoilValue(checkInOutData);
  const guests = useRecoilValue(guestsData);
  const prices = useRecoilValue(priceData);
  const [maps, setMaps] = useRecoilState(mapData);
  const [markerPositions, setMarkerPositions] = useState([]);

  const classes = useStyles();

  const { isLoading, data, error } = useFetch('userFilter', {
    checkIn: dates.체크인,
    checkOut: dates.체크아웃,
    minPrice: prices.최저가,
    maxPrice: prices.최고가,
    guests: Object.values(guests).reduce((a, b) => (a || 0) + (b || 0), 0),
  });

  useEffect(() => {
    const markerPositions = data?.reduce((acc, cur, i) => {
      return [...acc, { title: cur.name, lat: cur.y, lng: cur.x }];
    }, []);
    setMarkerPositions(markerPositions);
    // setMaps(markerPositions);
  }, [data, maps]);

  // const miniSearchBar = () => {
  //   return <MiniSearchBarWrapper></MiniSearchBarWrapper>;
  // };

  return (
    <Div>
      <NavBarWrapper>
        <NavBar />
      </NavBarWrapper>
      <div>
        <ListsWrapper>
          {isLoading && (
            <div className={classes.root}>
              <CircularProgress />
            </div>
          )}
          {data &&
            data.map((data: { [key: string]: any }, i: number) => {
              return (
                <ListWrapper key={i}>
                  <TitleImage
                    style={{ backgroundImage: `url(${data.title_image})` }}
                  />
                  <DetailWrapper>
                    <Region>
                      {data.region1} {data.region2}의 아파트 전체
                    </Region>
                    <Name>{data.name}</Name>
                    <Others>
                      최대 인원 {data.max_of_people}∙{data.type}∙침대
                      {data.number_of_room}개∙욕실{data.number_of_toilet}개
                    </Others>
                    <Others>
                      {data.options.map((option: string, i: number) => {
                        return (
                          <div key={i}>
                            <span>{option}</span>
                            {i < option.length - 2 && <span>∙</span>}
                          </div>
                        );
                      })}
                    </Others>
                    <Prices>
                      <DailyFee>
                        ₩{data.daily_fee.toLocaleString('en')} / 박
                      </DailyFee>
                      <TotalFee>총액 ₩{data.total_fee}</TotalFee>
                    </Prices>
                  </DetailWrapper>
                </ListWrapper>
              );
            })}
        </ListsWrapper>
        <MapWrapper>
          {markerPositions && markerPositions.length > 0 && (
            <SearchResultMap markerPositions={markerPositions} />
          )}
        </MapWrapper>
      </div>
    </Div>
  );
};

const Div = styled.div`
  max-height: 100vh;
`;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    '& > * + *': {
      marginLeft: theme.spacing(2),
    },
  },
}));

const NavBarWrapper = styled.div`
  background-color: white;
  box-shadow: 0px 0px 4px rgba(204, 204, 204, 0.5),
    0px 2px 4px rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(4px);
  height: 200px;
`;

const MapWrapper = styled.div`
  position: absolute;
  height: 100%;
  right: 0px;
  width: 50%;
  z-index: -1;
`;

const ListsWrapper = styled.div`
  position: absolute;
  width: 50%;
  left: 0px;
  padding: 2%;
  z-index: -2;
  display: flex;
  flex-direction: column;
  overflow: scroll;
  height: 100%;
`;
const ListWrapper = styled.div`
  display: flex;
  margin-bottom: 24px;
`;

const TitleImage = styled.div`
  width: 330px;
  height: 200px;
  border-radius: 10px;
  background-size: cover;
`;

const DetailWrapper = styled.div`
  position: relative;
  display: flex;
  width: 60%;
  flex-direction: column;
  margin-left: 24px;
  /* padding-left: 10px; */
`;

const Region = styled.div`
  font-size: 12px;
  margin: 8px 0px 4px 0px;

  /* Gray 3 */

  color: #828282;
`;

const Name = styled.div`
  font-size: 14px;

  /* Gray 1 */

  color: #333333;

  margin: 8px 0px;
`;

const Others = styled.div`
  font-size: 12px;

  /* Gray 3 */

  color: #828282;

  margin: 4px 0px;
`;

const Prices = styled.div`
  position: absolute;
  display: flex;
  flex-direction: column;
  right: 10px;
  bottom: 30px;
`;

const DailyFee = styled.div`
  font-weight: bold;
  font-size: 14px;
  margin-bottom: 6px;
  text-align: right;

  /* Gray 1 */

  color: #333333;
`;

const TotalFee = styled.div`
  font-size: 12px;

  text-align: right;
  text-decoration-line: underline;

  /* Gray 3 */

  color: #828282;
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

export default SearchResult;
