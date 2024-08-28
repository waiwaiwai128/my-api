// import Footer from '@/components/Footer';
// import { LockOutlined, UserOutlined } from '@ant-design/icons';
// import { LoginForm, ProFormText } from '@ant-design/pro-components';
// import { Helmet, history } from '@umijs/max';
// import {Alert, FloatButton, message, Tabs} from 'antd';
// import { createStyles } from 'antd-style';
// import React, { useState } from 'react';
// import Settings from '../../../../config/defaultSettings';
// import { userRegisterUsingPOST } from '@/services/yuapi-backend/userController';
//
//
// const useStyles = createStyles(({ token }) => {
//   return {
//     action: {
//       marginLeft: '8px',
//       color: 'rgba(0, 0, 0, 0.2)',
//       fontSize: '24px',
//       verticalAlign: 'middle',
//       cursor: 'pointer',
//       transition: 'color 0.3s',
//       '&:hover': {
//         color: token.colorPrimaryActive,
//       },
//     },
//     lang: {
//       width: 42,
//       height: 42,
//       lineHeight: '42px',
//       position: 'fixed',
//       right: 16,
//       borderRadius: token.borderRadius,
//       ':hover': {
//         backgroundColor: token.colorBgTextHover,
//       },
//     },
//     container: {
//       display: 'flex',
//       flexDirection: 'column',
//       height: '100vh',
//       overflow: 'auto',
//       backgroundImage:
//         "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
//       backgroundSize: '100% 100%',
//     },
//   };
// });
// /*const Lang = () => {
//   const { styles } = useStyles();
//   return;
// };*/
// const LoginMessage: React.FC<{
//   content: string;
// }> = ({ content }) => {
//   return (
//     <Alert
//       style={{
//         marginBottom: 24,
//       }}
//       message={content}
//       type="error"
//       showIcon
//     />
//   );
// };
// const Login: React.FC = () => {
//   const [type, setType] = useState<string>('account');
//   //const { styles } = useStyles();
//   const handleSubmit = async (values: API.UserRegisterRequest) => {
//     try {
//       // 注册
//       const res = await userRegisterUsingPOST({
//         ...values,
//       });
//       if (res.data) {
//         setTimeout(()=>{
//           const successMessage = '注册成功，请登录！';
//           message.success(successMessage);
//           history.push('/user/login');
//         },100);
//         return ;
//       }
//
//       }
//     catch (error){
//       const defaultLoginFailureMessage = '注册失败，请重试！' + error.message;
//       console.log(error);
//       message.error(defaultLoginFailureMessage);
//     }
//   };
//   return (
//     //<div className={styles.container}>
//     <div
//       style={{
//         display: 'flex',
//         flexDirection: 'column',
//         height: '100vh',
//         overflow: 'auto',
//         backgroundImage: "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
//         backgroundSize: '100% 100%',
//       }}
//     >
//       <Helmet>
//         <title>
//           {'注册'}- {Settings.title}
//         </title>
//       </Helmet>
//       {/*<Lang/>*/}
//       <div
//         style={{
//           flex: '1',
//           padding: '32px 0',
//         }}
//       >
//         <LoginForm
//           submitter={{
//             searchConfig: {
//               submitText: "注册"
//             }
//           }}
//           contentStyle={{
//             minWidth: 280,
//             maxWidth: '75vw',
//           }}
//           logo={<img alt="logo" src="http://47.120.1.12:9000/test/earth.svg"/>}
//           title="ZxApi 接口平台"
//           subTitle={'[ 在线调用 ] & [ 客户端SDK ]'}
//           initialValues={{
//             autoLogin: true,
//           }}
//           onFinish={async (values) => {
//             await handleSubmit(values as API.UserRegisterRequest);
//           }}
//         >
//           <Tabs
//             activeKey={type}
//             onChange={setType}
//             centered
//             items={[
//               {
//                 key: 'account',
//                 label: '注册',
//               }
//             ]}
//           />
//
//           {status === 'error' && loginType === 'account' && (
//             <LoginMessage content={'错误的用户名和密码(admin/ant.design)'}/>
//           )}
//           {type === 'account' && (
//             <>
//
//               <ProFormText
//                 name="userAccount"
//                 fieldProps={{
//                   size: 'large',
//                   prefix: <UserOutlined/>,
//                 }}
//                 placeholder={'用户名: '}
//                 rules={[
//                   {
//                     required: true,
//                     message: '用户名是必填项！',
//                   },
//                 ]}
//               />
//               <ProFormText.Password
//                 name="userPassword"
//                 fieldProps={{
//                   size: 'large',
//                   prefix: <LockOutlined/>,
//                 }}
//                 placeholder={'密码: '}
//                 rules={[
//                   {
//                     required: true,
//                     message: '密码是必填项！',
//                   },
//                 ]}
//               />
//               <ProFormText.Password
//                 name="checkPassword"
//                 fieldProps={{
//                   size: 'large',
//                   prefix: <LockOutlined/>,
//                 }}
//                 placeholder={'确认密码: '}
//                 rules={[
//                   {
//                     required: true,
//                     message: '密码是必填项！',
//                   },
//                 ]}
//               />
//             </>
//           )}
//         </LoginForm>
//         <FloatButton
//           tooltip={'已有账号？去登录'}
//           type="primary"
//           description="登录"
//           onClick={() => history.push('/user/login')}/>
//       </div>
//       <Footer/>
//     </div>
//   );
// };
// export default Login;

import { Helmet, history } from '@umijs/max';
import React, { useState } from 'react';
import { Input, Button, message } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { userRegisterUsingPOST } from '@/services/yuapi-backend/userController';

const Register: React.FC = () => {
  const [userAccount, setUserAccount] = useState('');
  const [userPassword, setUserPassword] = useState('');
  const [checkPassword, setCheckPassword] = useState('');

  const handleSubmit = async () => {
    if (userPassword !== checkPassword) {
      message.error('密码和确认密码不一致');
      return;
    }

    try {
      // 发送请求，包含 userAccount、userPassword 和 checkPassword
      const res = await userRegisterUsingPOST({ userAccount, userPassword, checkPassword });

      // 检查后端是否返回错误码或错误消息
      if (res.code === 0) {
        message.success('注册成功，请登录！');

        history.push('/user/login');
      } else {
        // 显示后端返回的错误消息
        message.error(`注册失败: ${res.message}`);
      }
    } catch (error) {
      // 处理其他错误情况
      if (error.response && error.response.data && error.response.data.message) {
        message.error(`注册失败: ${error.response.data.message}`);
      } else {
        message.error('注册失败，请重试！');
      }
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h1>注册</h1>
      <Input
        size="large"
        placeholder="用户名"
        prefix={<UserOutlined />}
        value={userAccount}
        onChange={(e) => setUserAccount(e.target.value)}
        style={{ marginBottom: 16 }}
      />
      <Input.Password
        size="large"
        placeholder="密码"
        prefix={<LockOutlined />}
        value={userPassword}
        onChange={(e) => setUserPassword(e.target.value)}
        style={{ marginBottom: 16 }}
      />
      <Input.Password
        size="large"
        placeholder="确认密码"
        prefix={<LockOutlined />}
        value={checkPassword}
        onChange={(e) => setCheckPassword(e.target.value)}
        style={{ marginBottom: 16 }}
      />
      <Button type="primary" size="large" onClick={handleSubmit}>
        注册
      </Button>
    </div>
  );
};

export default Register;

