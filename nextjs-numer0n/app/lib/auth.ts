import type { NextAuthOptions } from "next-auth"
import NextAuth from 'next-auth'
import Discord from "next-auth/providers/discord"

export const authOptions = {
    secret: process.env.NEXTAUTH_SECRET,
    providers: [
      Discord({
          clientId:     process.env.AUTH_DISCORD_ID     as string,
          clientSecret: process.env.AUTH_DISCORD_SECRET as string
      }),
    ],
    callbacks: {
      async session({ session, token }) {
        // セッションコールバックの返却値へアクセストークンを追加
        session.user.accessToken = token.accessToken;
        return session;
      },
      async jwt({ token, account }) {
        if (account) {
          token.accessToken = account.access_token
        }
        return token
      }
    }
  } satisfies NextAuthOptions

export const { handlers, auth, signIn, signOut } = NextAuth(authOptions)

