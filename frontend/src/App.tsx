import Main from './components/Main';
import CssBaseline from '@material-ui/core/CssBaseline';
import { RecoilRoot } from 'recoil';

function App() {
  return (
    <RecoilRoot>
      <CssBaseline />
      <div className="App">
        <Main />
      </div>
    </RecoilRoot>
  );
}

export default App;
