# ProcessMessageExchangeClient.py

# Define constants
MESSAGE_DELIMITER = "__DEL__"
MESSAGE_END_OF_PROCESS = "__EOP__"

# Function to get a request from the user (stdin)
def get_server_request():
    return input()

# Function to reply with a response and delimiter
def reply_response(response):
    print(response)
    print(MESSAGE_DELIMITER)

# Function to process messages
def process_message(process_request):
    try:
        message = ""

        while True:
            input_data = get_server_request()

            if input_data is None:
                break

            if input_data == MESSAGE_END_OF_PROCESS:
                break

            if input_data == MESSAGE_DELIMITER:
                message = process_request(message)
                reply_response(message)
                message = ""
                continue

            if message == "":
                message = input_data
            else:
                message += f"\n{input_data}"

    except Exception as e:
        print(e)

# Call the main message processing function
if __name__ == "__main__":
    process_message(None)
