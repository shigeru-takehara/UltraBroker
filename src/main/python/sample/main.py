count = 0

# Example custom ProcessRequest function
def custom_process_request(request):
    global count
    # Your custom processing logic here
    count += 1
    return f"Custom processing: {request} {count}"

# Run ProcessMessageExchangeClient.py with the custom ProcessRequest function
if __name__ == "__main__":
    from ProcessMessageExchangeClient import process_message
    process_message(custom_process_request)
