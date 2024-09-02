'use client';
import React from 'react';
import { useSession, signIn, signOut } from 'next-auth/react';

export default function Home() {
  const { data: session } = useSession();
  return (
    <main className='flex min-h-screen  p-6 grid place-items-center grid-cols-1 grid-rows-1'>
      <div className='z-10 max-w-5xl w-full items-center justify-between font-mono text-sm lg:flex text-center '>
        {!session && (
          <button
            onClick={() => signIn(undefined, { callbackUrl: "http://localhost:3000/" })}
            className='bg-blue-700 rounded-md shadow-md text-white h-8 w-32 hover:bg-blue-600'
          >
            Sign In
          </button>
        )}
        
        {session && (
          <button
            onClick={() => signOut()}
            className='bg-blue-700 rounded-md shadow-md text-white h-8 w-32 hover:bg-blue-600'
          >
            Sign Out
          </button>
        )}
      </div>
    </main>
  );
}
