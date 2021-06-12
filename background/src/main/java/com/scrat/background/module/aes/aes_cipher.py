from Cryptodome.Cipher import AES # pip install pycryptodome
from Cryptodome.Util.Padding import pad, unpad
from base64 import b64decode, b64encode

class AESCipher:
    def __init__(self, secret: str, iv: str):
        self.key = secret
        self.iv = iv

    def encrypt(self, text):
        """
        1: padding，2: aes encrypt, 3: base64 encode
        :param text: Text that needs to be encrypted
        :return: base64 encode string
        """
        text = pad(text.encode(), AES.block_size)  # 包pycryptodome 的加密函数不接受str
        cipher = AES.new(key=self.key.encode('utf-8'), mode=AES.MODE_CBC, IV=self.iv.encode())
        encrypted_text = cipher.encrypt(text)
        return b64encode(encrypted_text).decode('utf-8')

    def decrypt(self, encrypted_text):
        """
        :param encrypted_text : Text that needs to be decrypted
        :return: origin text
        """
        encrypted_text = b64decode(encrypted_text)
        cipher = AES.new(key=self.key.encode(), mode=AES.MODE_CBC, IV=self.iv.encode())
        decrypted_text = cipher.decrypt(encrypted_text)
        return unpad(decrypted_text, AES.block_size).decode('utf-8')

secret = 'wfxAbPXiGMHRCdE7'
iv = 'wfxAbPXiGMHRCdE7'
text = 'Hello!'

encrypted_text = AESCipher(secret, iv).encrypt(text)
print(encrypted_text)
decrypted_text = AESCipher(secret, iv).decrypt(encrypted_text)
print(decrypted_text)
print(text == decrypted_text)