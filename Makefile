# Java CQRS Project Makefile
# This Makefile provides convenient commands to manage Docker Compose services

.PHONY: help build up down clean discovery discovery-up discovery-down full full-up full-down restart discovery-restart status

# Default target
help:
	@echo "Available targets:"
	@echo "  help              - Show this help message"
	@echo ""
	@echo "Full Stack (docker-compose.yml):"
	@echo "  full-up           - Start all services (discovery, gateway, products, axon, postgres)"
	@echo "  full-down         - Stop all services"
	@echo "  full-restart      - Restart all services"
	@echo ""
	@echo "Discovery Only (docker-compose-discovery.yml):"
	@echo "  discovery-up      - Start discovery services (discovery, gateway, axon, postgres)"
	@echo "  discovery-down    - Stop discovery services"
	@echo "  discovery-restart - Restart discovery services"
	@echo ""
	@echo "General Commands:"
	@echo "  build             - Build all Docker images"
	@echo "  clean             - Remove all containers, volumes, and images"
	@echo "  status            - Show status of all containers"
	@echo ""
	@echo "Shortcuts:"
	@echo "  up                - Alias for full-up"
	@echo "  down              - Alias for full-down"
	@echo "  restart           - Alias for full-restart"
	@echo "  discovery         - Alias for discovery-up"

# Full stack commands (docker-compose.yml)
full-up:
	@echo "Starting full stack services..."
	docker-compose up -d

full-down:
	@echo "Stopping full stack services..."
	docker-compose down

full-restart:
	@echo "Restarting full stack services..."
	docker-compose restart

# Discovery only commands (docker-compose-discovery.yml)
discovery-up:
	@echo "Starting discovery services..."
	docker-compose -f docker-compose-discovery.yml up -d

discovery-down:
	@echo "Stopping discovery services..."
	docker-compose -f docker-compose-discovery.yml down

discovery-restart:
	@echo "Restarting discovery services..."
	docker-compose -f docker-compose-discovery.yml restart

# Build commands
build:
	@echo "Building all Docker images..."
	docker-compose build
	docker-compose -f docker-compose-discovery.yml build

# General commands
clean:
	@echo "Stopping and removing all containers, networks, and volumes..."
	docker-compose down -v --remove-orphans
	docker-compose -f docker-compose-discovery.yml down -v --remove-orphans
	@echo "Removing unused Docker images..."
	docker image prune -f
	@echo "Removing unused Docker volumes..."
	docker volume prune -f

status:
	@echo "Docker containers status:"
	@docker ps -a --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

# Convenient aliases
up: full-up
down: full-down
restart: full-restart
discovery: discovery-up