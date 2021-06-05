import Main from './components/Main';
import SearchResult from './components/SearchResult';
import Login from './components/Login';
import CssBaseline from '@material-ui/core/CssBaseline';
import { RecoilRoot } from 'recoil';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <CssBaseline />
        <div className="App">
          <BrowserRouter>
            <Switch>
              <Route path="/" exact>
                <Main />
              </Route>
              <Route path="/SearchResult">
                <SearchResult />
              </Route>
              <Route path="/oauth/login" component={Login} />
            </Switch>
          </BrowserRouter>
        </div>
      </RecoilRoot>
    </QueryClientProvider>
  );
}

export default App;
