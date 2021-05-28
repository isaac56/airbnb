import { atom, RecoilState } from 'recoil';

const filterDisplayAtom = atom({
  key: 'display',
  default: {
    체크인: '입력 날짜',
    체크아웃: '입력 날짜',
    요금: '금액대 설정',
    인원: '게스트 추가',
  },
});

const filterDataAtom: RecoilState<any> = atom({
  key: 'datas',
  default: {
    체크인: null,
    체크아웃: null,
    요금: null,
  },
});

const guestsDataAtom: RecoilState<any> = atom({
  key: 'guests',
  default: {
    성인: 0,
    어린이: 0,
    유아: 0,
  },
});

export { filterDisplayAtom, filterDataAtom, guestsDataAtom };
