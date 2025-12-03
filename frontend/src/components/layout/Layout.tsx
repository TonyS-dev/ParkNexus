import React from 'react';
import { Header } from './Header';
import { Sidebar } from './Sidebar';

export const Layout: React.FC<{children?: React.ReactNode}> = ({children}) => (
  <div>
    <Header />
    <Sidebar />
    <main>{children}</main>
  </div>
);

