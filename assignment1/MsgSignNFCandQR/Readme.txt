In these apps, a message coding a selection of items in a list, minimally,
is built (minimizing the number of bytes needed) and signed using the private
key of an RSA key pair generated in the device. Then it is transmitted and
verified on the other side.
The keys are kept in the Android KeyStore. That way, even the process that has
generated the key pair can not have access to the private key bytes, only to the
result of cryptographic operations ...

SelectAndNFC
============

Builds the message from the user selection and signs it with the private key.
Can transmit this message (signed) via NFC or generate a QR code with it.
Can also transmit the public key via NFC.

TerminalNFC
===========

Can receive and store (in memory) a public key.
Can also receive through NFC a signed message containing an encoded selection
from the other app. Using the public key (if it has one) verifies the authenticity
of signature and displays the selection of items contained in the message.
It can also read the same signed message using the QRCode generated in the other app.