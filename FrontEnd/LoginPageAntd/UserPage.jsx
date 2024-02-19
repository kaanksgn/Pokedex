import React, { useState } from 'react';
import {
  DesktopOutlined,
  LogoutOutlined,
  FileOutlined,
  PieChartOutlined,
  TeamOutlined,
  UserOutlined,
} from '@ant-design/icons';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
import UserDashboard from './UserDashboard';
import UserOperations from './UserOperations';
import UserPokeOperations from './UserPokeOperations';
import axios from '../../api/axios';
import AppAntd from './AppAntd';

const { Header, Content, Footer, Sider } = Layout;


function getItem(
  label ,
  key ,
  icon,
  children
) {
  return {
    key,
    icon,
    children,
    label,
  } ;
}

const items = [
  getItem('User Dashboard', 'dashboard', <PieChartOutlined />),
  getItem('Pokemon Operations', 'pokeOp', <DesktopOutlined />),
  getItem('Logout','logout',<LogoutOutlined className='logout'/>)
];


const AdminPage = ({setLogedInUser}) => {
  const [collapsed, setCollapsed] = useState(false);
  const [selectedTab, setselectedTab] = useState('dashboard')

  const onSelect=({item,key,keyPath,selectedKeys,domEvent})=>{
    
    setselectedTab(key)
    if(key=='logout'){
        console.log("Log Out")
        axios.get("/logout")
        window.sessionStorage.removeItem("user")
        setLogedInUser(null)
    }
}
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (<div id="logindiv">
    <Layout style={{ minHeight: '100vh' }}>
      <Sider className='sider'  collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
        <div className="demo-logo-vertical" />
        <Menu theme="dark" defaultSelectedKeys={['dashboard']} mode="inline" items={items} onSelect={onSelect} />
      </Sider>
      <Layout>
        <Header className="headerField"style={{ padding: 0, backgroundImage:"none", }} >User Dashboard</Header>
        <Content className='adminContent'>
          {selectedTab == 'dashboard' ? <UserDashboard/> : selectedTab=='pokeOp' ? <UserPokeOperations/>: null
          }
        </Content>
       
      </Layout>
    </Layout></div>
  );
};

export default AdminPage;