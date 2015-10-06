class StdoutHandler
  def on_subscribe

  end

  def handle_message(message)
    puts message
  end
end