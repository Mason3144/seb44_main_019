import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import GlobalStyle from './style/GlobalStyle.ts';
import Nav from './component/nav/Nav.tsx';
// import Header from './Components/Common/Header/Header';
// import Sidebar from './Components/Common/Sidebar/Sidebar';
// import Footer from './Components/Common/Footer/Footer';
// import HomePage from './Pages/Common/HomePage/HomePage';
// import MainPage from './Pages/Common/MainPage/MainPage';
// import LoginPage from './Pages/LoginSignup/LoginPage.tsx';
// import MyPage from './Pages/My/MyPage.jsx';
// import SignupPage from './Pages/LoginSignup/SingupPage.jsx';
// import AfterSignupPage from './Pages/LoginSignup/AfterSignupPage.jsx';


function App() {
  return (
    <BrowserRouter>
      <GlobalStyle />
      <Nav />
      <main>
        <Routes>
          {/* <Route path="/" element={} /> // 홈페이지
          <Route path="/main" element={} /> // 메인페이지
          <Route path="/login" element={} /> // 로그인
          <Route path="/singup" element={} /> // 회원가입
          <Route path="/signup/success" element={} /> // 회원가입 완료
*/}

          <Route path="/shelllist" element={<ShellList/>} /> 
{/*       <Route path="/shellcreate" element={} /> // 제품 생성 페이지
          <Route path="/shelldetail/:id" element={} /> // 제품 상세 페이지
          <Route path="/shellupdate" element={} /> // 제품 수정 페이지
          <Route path="/member/:id" element={} /> // 마이 페이지
          <Route path="/dm/:id" element={} /> // DM 페이지
          <Route path="/offer/:id" element={} /> // 요청함 페이지 */}
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;
