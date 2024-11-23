import { OAuth2Client } from 'google-auth-library';
const client = new OAuth2Client();

async function verifyToken(token) {
    client.setCredentials({ access_token: token });
    const userinfo = await client.request({
      url: "https://www.googleapis.com/oauth2/v3/userinfo",
    });
    return userinfo.data;
}

export default {
  verifyToken
};