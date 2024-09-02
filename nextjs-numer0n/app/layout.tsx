import NextAuthProvider from '@/providers/NextAuth';
import './ui/global.css';
import React from 'react';

export default function RootLayout({
  children
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body><NextAuthProvider>{children}</NextAuthProvider></body>
    </html>
  );
}
