import { DefaultSession } from "next-auth";

declare module "next-auth" {
  interface Session {
    user: {
      accessToken?: string;
    } & DefaultSession["user"];
  }
}

// セッションでアクセストークンを保持できるように拡張
declare module "next-auth/jwt" {
    interface JWT {
      accessToken?: string;
    }
}